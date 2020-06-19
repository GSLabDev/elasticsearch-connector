/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.internal.operations;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.ClearScrollResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.mule.runtime.extension.api.annotation.metadata.OutputResolver;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.mule.runtime.extension.api.runtime.process.CompletionCallback;
import org.apache.log4j.Logger;

import com.mulesoft.connectors.elasticsearch.api.JsonData;
import com.mulesoft.connectors.elasticsearch.api.SearchRequestConfiguration;
import com.mulesoft.connectors.elasticsearch.api.SearchRequestOptionalConfiguration;
import com.mulesoft.connectors.elasticsearch.api.SearchSourceConfiguration;
import com.mulesoft.connectors.elasticsearch.api.querytype.Query;
import com.mulesoft.connectors.elasticsearch.api.response.ElasticsearchResponse;
import com.mulesoft.connectors.elasticsearch.internal.connection.ElasticsearchConnection;
import com.mulesoft.connectors.elasticsearch.internal.error.ElasticsearchErrorTypes;
import com.mulesoft.connectors.elasticsearch.internal.error.exception.ElasticsearchException;
import com.mulesoft.connectors.elasticsearch.internal.metadata.ClearScrollResponseOutputMetadataResolver;
import com.mulesoft.connectors.elasticsearch.internal.metadata.ResponseOutputMetadataResolver;
import com.mulesoft.connectors.elasticsearch.internal.utils.ElasticsearchUtils;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 * 
 *         ElasticSearch search, search scroll, search using JSON data and clear search scroll operations
 */
public class SearchOperations extends BaseSearchOperation {

    private static final Logger logger = Logger.getLogger(SearchOperations.class.getName());

    /**
     * The search operation returns search hits that match the query defined in the request.
     * 
     * @param esConnection
     *            The Elasticsearch connection
     * @param searchRequestConfiguration
     *            Search request configuration
     * @param queryConfiguration
     *            Different types of Elasticsearch query configuration
     * @param searchSourceConfiguration
     *            Search source configuration to control the search behavior.
     * @param searchRequestOptionalConfiguration
     *            Some optional parameters for search request configuration
     * @param callback
     */
    @MediaType(value = MediaType.APPLICATION_JSON, strict = false)
    @DisplayName("Search - Query")
    public void search(@Connection ElasticsearchConnection esConnection, @ParameterGroup(name = "Search") SearchRequestConfiguration searchRequestConfiguration,
            @DisplayName("Query Type") @Placement(order = 1, tab = "Query") Query queryConfiguration,
            @DisplayName("Search Source") @Placement(order = 2, tab = "Search Source") @Optional SearchSourceConfiguration searchSourceConfiguration,
            @DisplayName("Search Config") @Placement(order = 3, tab = "Search Optional Parameters") @Optional SearchRequestOptionalConfiguration searchRequestOptionalConfiguration,
            CompletionCallback<SearchResponse, Void> callback) {

        SearchSourceBuilder searchSourceBuilder = searchSourceConfiguration != null ? getSearchSourceBuilderOptions(searchSourceConfiguration) : new SearchSourceBuilder();
        searchSourceBuilder.query(queryConfiguration.getQuery());
        SearchRequest searchRequest = getSearchRequest(searchRequestConfiguration, searchRequestOptionalConfiguration);
        searchRequest.source(searchSourceBuilder);

        try {
            SearchResponse response = esConnection.getElasticsearchConnection().search(searchRequest, ElasticsearchUtils.getContentTypeJsonRequestOption());
            logger.info("Search response : " + response);
            responseConsumer(response, callback);
        } catch (Exception e) {
            throw new ElasticsearchException(ElasticsearchErrorTypes.OPERATION_FAILED, e);
        }
    }

    /**
     * This search operation returns search hits based on JSON data.
     * 
     * @param esConnection
     *            The Elasticsearch connection
     * @param index
     *            Restricts the search request to an index
     * @param jsonData
     *            JSON file or string containing Elasticsearch query configuration
     * @return Search Result as JSON String
     */
    @MediaType(value = MediaType.APPLICATION_JSON)
    @DisplayName("Search - JSON Query")
    @OutputResolver(output = ResponseOutputMetadataResolver.class)
    public String searchUsingJsonData(@Connection ElasticsearchConnection esConnection, @Optional String index, @ParameterGroup(name = "JSON Query") JsonData jsonData) {
        String result;
        String resource = index != null ? index.trim() + "/_search" : "/_search";
        String jsonContent;

        if (jsonData.getJsonfile() != null) {
            try {
                jsonContent = ElasticsearchUtils.readFileToString(jsonData.getJsonfile());
            } catch (Exception e) {
                throw new ElasticsearchException(ElasticsearchErrorTypes.OPERATION_FAILED, e);
            }
        } else {
            jsonContent = jsonData.getJsonText();
        }

        HttpEntity entity = new NStringEntity(jsonContent, ContentType.APPLICATION_JSON);
        Map<String, String> params = Collections.singletonMap("pretty", "true");
        ElasticsearchResponse response;
        try {
            Request request = new Request(HttpGet.METHOD_NAME, "/" + resource);
            request.addParameters(params);
            request.setEntity(entity);
            response = new ElasticsearchResponse(esConnection.getElasticsearchConnection().getLowLevelClient().performRequest(request));

            logger.debug("RequestLine:" + response.getRequestLine());
            logger.info("Search response : " + response);
            
            result = getJsonResponse(response);
        } catch (Exception e) {
            throw new ElasticsearchException(ElasticsearchErrorTypes.OPERATION_FAILED, e);
        }
        return result;
    }
    
    /**
     * The Search Scroll operation is used to retrieve a large number of results from a search request.
     * @param esConnection
     *            The Elasticsearch connection
     * @param scrollId
     *            Scroll identifier returned in last scroll/search request
     * @param timeValue
     *            Scroll time interval (in seconds) to keep the search context alive
     * @param callback
     */
    @MediaType(value = MediaType.APPLICATION_JSON, strict = false)
    @DisplayName("Search - Scroll")
    public void searchScroll(@Connection ElasticsearchConnection esConnection, @Summary("Scroll identifier returned in last scroll/search request") String scrollId,
            @Optional @DisplayName("Keep alive time") @Summary("Time interval (in seconds) to keep the search context alive.") long timeValue,
            CompletionCallback<SearchResponse, Void> callback) {

        SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
        
        if(timeValue != 0) {
            scrollRequest.scroll(new Scroll(TimeValue.timeValueSeconds(timeValue)));
        }
        
        SearchResponse searchResponse;
        try {
            searchResponse = esConnection.getElasticsearchConnection().scroll(scrollRequest, RequestOptions.DEFAULT);
            logger.info("Search response : " + searchResponse);
            responseConsumer(searchResponse, callback);
        } catch (Exception e) {
            throw new ElasticsearchException(ElasticsearchErrorTypes.OPERATION_FAILED, e);
        }
    }

    /**
     * Clear scroll operation release search contexts as soon as they are not necessary anymore.
     * This happens automatically when the scroll expires
     * 
     * @param esConnection
     *            The Elasticsearch connection
     * @param scrollIds
     *            List of scroll identifiers to clear
     * @return ClearScrollResponse as JSON String
     */
    @MediaType(value = MediaType.APPLICATION_JSON)
    @DisplayName("Search - Clear Scroll")
    @OutputResolver(output = ClearScrollResponseOutputMetadataResolver.class)
    public String clearScroll(@Connection ElasticsearchConnection esConnection, @DisplayName("Scroll IDs") List<String> scrollIds) {
        String result = null;
        
        ClearScrollRequest clearScrollrequest = new ClearScrollRequest();
        clearScrollrequest.setScrollIds(scrollIds);
        try {
            ClearScrollResponse response = esConnection.getElasticsearchConnection().clearScroll(clearScrollrequest, RequestOptions.DEFAULT);
            logger.info("Clear scroll response : " + response);
            result = getJsonResponse(response);
        } catch (Exception e) {
            throw new ElasticsearchException(ElasticsearchErrorTypes.OPERATION_FAILED, e);
        }
        return result;
    }
}
