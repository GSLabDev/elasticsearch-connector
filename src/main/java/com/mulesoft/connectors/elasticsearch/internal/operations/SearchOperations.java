/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.internal.operations;

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

import com.mulesoft.connectors.elasticsearch.api.JsonData;
import com.mulesoft.connectors.elasticsearch.api.SearchRequestConfiguration;
import com.mulesoft.connectors.elasticsearch.api.SearchSourceConfiguration;
import com.mulesoft.connectors.elasticsearch.api.querytype.Query;
import com.mulesoft.connectors.elasticsearch.internal.connection.ElasticsearchConnection;
import com.mulesoft.connectors.elasticsearch.internal.error.ElasticsearchError;
import com.mulesoft.connectors.elasticsearch.internal.error.exception.ElasticsearchException;
import com.mulesoft.connectors.elasticsearch.internal.utils.ElasticsearchUtils;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 * 
 *         ElasticSearch search, search scroll, search using JSON data and clear search scroll operations
 */
public class SearchOperations extends BaseSearchOperation {

    private static final Logger logger = LoggerFactory.getLogger(SearchOperations.class);

    /**
     * 
     * @param esConnection
     *            The Elasticsearch connection
     * @param searchRequestConfiguration
     *            Search request configuration
     * @param queryConfiguration
     *            Different types of Elasticsearch query query configuration
     * @param searchSourceConfiguration
     *            Search source configuration to control the search behavior.
     * @return SearchResponse
     */

    @MediaType(value = MediaType.APPLICATION_JSON, strict = false)
    @DisplayName("Search - Query")
    public SearchResponse search(@Connection ElasticsearchConnection esConnection, @ParameterGroup(name = "Search") SearchRequestConfiguration searchRequestConfiguration,
            @DisplayName("Query Type") @Placement(order = 1, tab = "Query") Query queryConfiguration,
            @DisplayName("Search Source") @Placement(order = 2, tab = "Search Source") @Optional SearchSourceConfiguration searchSourceConfiguration) {

        SearchSourceBuilder searchSourceBuilder = searchSourceConfiguration != null ? getSearchSourceBuilderOptions(searchSourceConfiguration) : new SearchSourceBuilder();
        searchSourceBuilder.query(queryConfiguration.getQuery());
        SearchRequest searchRequest = getSearchRequest(searchRequestConfiguration);
        searchRequest.source(searchSourceBuilder);

        try {
            return esConnection.getElasticsearchConnection().search(searchRequest, ElasticsearchUtils.getContentTypeJsonHeader());
        } catch (Exception e) {
            throw new ElasticsearchException(ElasticsearchError.OPERATION_FAILED, e);
        }
    }

    /**
     * 
     * @param esConnection
     *            The Elasticsearch connection
     * @param scrollId
     *            Scroll identifier returned in last scroll request
     * @param timeValue
     *            Set the scroll interval time keep the search context alive(minutes)
     * @return SearchResponse
     */

    @MediaType(value = MediaType.APPLICATION_JSON, strict = false)
    @DisplayName("Search - Scroll")
    public SearchResponse searchScroll(@Connection ElasticsearchConnection esConnection, @Summary("Scroll identifier returned in last request") String scrollId,
            @DisplayName("Keep alive time") @Summary("Keep the search context alive for the minutes time") long timeValue) {

        SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
        scrollRequest.scroll(new Scroll(TimeValue.timeValueMinutes(timeValue)));

        SearchResponse searchResponse;
        try {
            searchResponse = esConnection.getElasticsearchConnection().searchScroll(scrollRequest);
        } catch (Exception e) {
            throw new ElasticsearchException(ElasticsearchError.OPERATION_FAILED, e);
        }
        logger.debug("search response" + searchResponse);
        return searchResponse;
    }

    /**
     * 
     * @param esConnection
     *            The Elasticsearch connection
     * @param index
     *            Restricts the search request to an index
     * @param jsonData
     *            JSON file or string containing Elasticsearch query configuration
     * @return Search Result
     */

    @MediaType(value = MediaType.APPLICATION_JSON, strict = false)
    @DisplayName("Search - JSON Query")
    public Result<String, StatusLine> searchUsingJsonData(@Connection ElasticsearchConnection esConnection, @Optional String index,
            @ParameterGroup(name = "JSON Query") JsonData jsonData) {

        String resource = index != null ? index.trim() + "/_search" : "/_search";
        String jsonContent;

        if (jsonData.getJsonfile() != null) {
            try {
                jsonContent = ElasticsearchUtils.readFileToString(jsonData.getJsonfile());
            } catch (Exception e) {
                throw new ElasticsearchException(ElasticsearchError.OPERATION_FAILED, e);
            }
        } else {
            jsonContent = jsonData.getJsonText();
        }

        HttpEntity entity = new NStringEntity(jsonContent, ContentType.APPLICATION_JSON);
        Map<String, String> params = Collections.singletonMap("pretty", "true");
        Response response;
        try {
            response = esConnection.getElasticsearchConnection().getLowLevelClient().performRequest(HttpGet.METHOD_NAME, "/" + resource, params, entity);

            logger.debug("RequestLine:" + response.getRequestLine());
            String responseBody = EntityUtils.toString(response.getEntity());

            return Result.<String, StatusLine>builder()
                    .output(responseBody)
                    .attributes(response.getStatusLine())
                    .length(response.getEntity().getContentLength())
                    .mediaType(org.mule.runtime.api.metadata.MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            throw new ElasticsearchException(ElasticsearchError.OPERATION_FAILED, e);

        }
    }

    /**
     * The search contexts used by the Search Scroll operation are automatically deleted when the scroll times out. Clear scroll operation release search contexts as soon as they
     * are not necessary anymore using the Clear Scroll.
     * 
     * @param esConnection
     *            The Elasticsearch connection
     * @param scrollId
     *            Scroll identifier to clear scroll
     * @return ClearScrollResponse
     */
    @MediaType(value = MediaType.APPLICATION_JSON, strict = false)
    @DisplayName("Search - Clear Scroll")
    public ClearScrollResponse clearScroll(@Connection ElasticsearchConnection esConnection, @DisplayName("Scroll ID") String scrollId) {
        ClearScrollRequest clearScrollrequest = new ClearScrollRequest();
        clearScrollrequest.addScrollId(scrollId);
        try {
            return esConnection.getElasticsearchConnection().clearScroll(clearScrollrequest);
        } catch (Exception e) {
            throw new ElasticsearchException(ElasticsearchError.OPERATION_FAILED, e);
        }
    }
}
