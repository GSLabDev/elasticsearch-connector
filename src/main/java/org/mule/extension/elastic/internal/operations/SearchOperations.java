/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.extension.elastic.internal.operations;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.ClearScrollResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.Response;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.mule.extension.elastic.api.JsonData;
import org.mule.extension.elastic.api.SearchRequestConfiguration;
import org.mule.extension.elastic.api.SearchSourceConfiguration;
import org.mule.extension.elastic.api.querytype.Query;
import org.mule.extension.elastic.internal.connection.ElasticsearchConnection;
import org.mule.extension.elastic.internal.utils.ElasticsearchUtils;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 * 
 *         ElasticSearch search, search scroll, search using JSON data and clear search scroll operations
 */
public class SearchOperations {

    private static final Logger logger = LoggerFactory.getLogger(SearchOperations.class);

    /**
     * 
     * @param esConnection
     *            The elastic search connection
     * @param searchRequestConfiguration
     *            Search request configuration
     * @param queryConfiguration
     *            Different types of elastic search query query configuration
     * @param searchSourceConfiguration
     *            Search source configuration to control the search behavior.
     * @return SearchResponse
     * @throws IOException
     */

    @MediaType(value = MediaType.APPLICATION_JSON, strict = false)
    public SearchResponse search(@Connection ElasticsearchConnection esConnection, @ParameterGroup(name = "Search") SearchRequestConfiguration searchRequestConfiguration,
            @DisplayName("Query Type") @Placement(order = 1, tab = "Query") Query<? extends QueryBuilder> queryConfiguration,
            @DisplayName("Search Source") @Placement(order = 2, tab = "Search Source") @Optional SearchSourceConfiguration searchSourceConfiguration) throws IOException {

        SearchSourceBuilder searchSourceBuilder = searchSourceConfiguration != null ? searchSourceConfiguration.getSearchSourceBuilderOptions() : new SearchSourceBuilder();
        searchSourceBuilder.query(queryConfiguration.getQuery());
        SearchRequest searchRequest = searchRequestConfiguration.getSearchRequest();
        searchRequest.source(searchSourceBuilder);

        return esConnection.getElasticsearchConnection().search(searchRequest, ElasticsearchUtils.getContentTypeJsonHeader());
    }

    /**
     * 
     * @param esConnection
     *            The elastic search connection
     * @param scrollId
     *            Scroll identifier returned in last scroll request
     * @param timeValue
     *            Set the scroll interval time keep the search context alive(minutes)
     * @return SearchResponse
     * @throws IOException
     */

    @MediaType(value = MediaType.APPLICATION_JSON, strict = false)
    public SearchResponse searchScroll(@Connection ElasticsearchConnection esConnection, @Summary("Scroll identifier returned in last request") String scrollId,
            @DisplayName("Keep alive time") @Summary("Keep the search context alive for the minutes time") long timeValue) throws IOException {

        SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
        scrollRequest.scroll(new Scroll(TimeValue.timeValueMinutes(timeValue)));

        SearchResponse searchResponse = esConnection.getElasticsearchConnection().searchScroll(scrollRequest);
        logger.debug("search response" + searchResponse);
        return searchResponse;
    }

    /**
     * 
     * @param esConnection
     *            The elastic search connection
     * @param index
     *            Restricts the search request to an index
     * @param jsonData
     *            JSON file or string containing elastic search query configuration
     * @return Search Result
     * @throws IOException
     */

    @MediaType(value = MediaType.APPLICATION_JSON, strict = false)
    public Result<String, StatusLine> searchUsingJsonData(@Connection ElasticsearchConnection esConnection, @Optional String index,
            @ParameterGroup(name = "JSON Query") JsonData jsonData) throws IOException {

        String resource = index != null ? index.trim() + "/_search" : "/_search";
        String jsonContent;

        if (jsonData.getJsonfile() != null) {
            jsonContent = ElasticsearchUtils.readFileToString(jsonData.getJsonfile());
        } else {
            jsonContent = jsonData.getJsonText();
        }

        HttpEntity entity = new NStringEntity(jsonContent, ContentType.APPLICATION_JSON);
        Map<String, String> params = Collections.singletonMap("pretty", "true");
        Response response = esConnection.getElasticsearchConnection().getLowLevelClient().performRequest(HttpGet.METHOD_NAME, "/" + resource, params, entity);

        logger.debug("RequestLine:" + response.getRequestLine());
        String responseBody = EntityUtils.toString(response.getEntity());

        return Result.<String, StatusLine>builder()
                .output(responseBody)
                .attributes(response.getStatusLine())
                .length(response.getEntity().getContentLength())
                .mediaType(org.mule.runtime.api.metadata.MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     * The search contexts used by the Search Scroll operation are automatically deleted when the scroll times out. Clear scroll operation release search contexts as soon as they
     * are not necessary anymore using the Clear Scroll.
     * 
     * @param esConnection
     *            The elastic search connection
     * @param scrollId
     *            Scroll identifier to clear scroll
     * @return
     * @throws IOException
     */
    @MediaType(value = MediaType.APPLICATION_JSON, strict = false)
    public ClearScrollResponse clearScroll(@Connection ElasticsearchConnection esConnection, @DisplayName("Scroll ID") String scrollId) throws IOException {
        ClearScrollRequest clearScrollrequest = new ClearScrollRequest();
        clearScrollrequest.addScrollId(scrollId);
        return esConnection.getElasticsearchConnection().clearScroll(clearScrollrequest);
    }
}
