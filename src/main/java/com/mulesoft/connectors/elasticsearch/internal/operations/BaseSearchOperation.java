/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.internal.operations;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.mule.runtime.extension.api.annotation.Ignore;

import com.mulesoft.connectors.elasticsearch.api.SearchRequestConfiguration;
import com.mulesoft.connectors.elasticsearch.api.SearchSourceConfiguration;

public class BaseSearchOperation extends ElasticsearchOperations {

    /**
     * SearchRequest Builder
     * 
     * @param searchRequestConfig
     *            Search Request configuration parameters
     * @return SearchRequest
     */
    @Ignore
    protected SearchRequest getSearchRequest(SearchRequestConfiguration searchRequestConfig) {
        SearchRequest searchRequest = new SearchRequest();

        if (searchRequestConfig.getIndex() != null) {
            searchRequest.indices(searchRequestConfig.getIndex());
        }

        searchRequest.searchType(searchRequestConfig.getSearchType() != null ? SearchType.valueOf(searchRequestConfig.getSearchType().name()) : SearchType.DEFAULT);

        if (searchRequestConfig.getType() != null) {
            // Limits the request to a type
            searchRequest.types(searchRequestConfig.getType());
        }

        if (searchRequestConfig.getRouting() != null) {
            // Set a routing parameter
            searchRequest.routing(searchRequestConfig.getRouting());
        }

        if (searchRequestConfig.getScrollIntervalTime() != 0) {
            searchRequest.scroll(new Scroll(TimeValue.timeValueMinutes(searchRequestConfig.getScrollIntervalTime())));
        }

        return searchRequest;
    }

    /**
     * Create and returns the search source builder
     * 
     * @return SearchSourceBuilder
     */
    @Ignore
    protected SearchSourceBuilder getSearchSourceBuilderOptions(SearchSourceConfiguration searchSourceConfig) {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        if (searchSourceConfig.getTimeout() != 0) {
            searchSourceBuilder.timeout(TimeValue.timeValueMinutes(searchSourceConfig.getTimeout()));
        }

        if (searchSourceConfig.getSortOrder() != null) {
            searchSourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.fromString(searchSourceConfig.getSortOrder().name())));
        }

        if (searchSourceConfig.getSortByFieldName() != null) {
            searchSourceBuilder.sort(new FieldSortBuilder(searchSourceConfig.getSortByFieldName())
                    .order(searchSourceConfig.getSortOrder() != null ? SortOrder.fromString(searchSourceConfig.getSortOrder().name()) : SortOrder.DESC));
        }

        searchSourceBuilder.fetchSource(searchSourceConfig.isFetchSource());

        if (searchSourceConfig.getIncludeFields() != null && searchSourceConfig.getExcludeFields() != null) {

            searchSourceBuilder.fetchSource(searchSourceConfig.getIncludeFields().toArray(new String[0]), searchSourceConfig.getExcludeFields().toArray(new String[0]));
        }

        return searchSourceBuilder.from(searchSourceConfig.getFrom())
                .size(searchSourceConfig.getSize())
                .profile(searchSourceConfig.isProfile())
                .explain(searchSourceConfig.isExplain())
                .terminateAfter(searchSourceConfig.getTerminateAfter())
                .trackScores(searchSourceConfig.isTrackScores())
                .trackTotalHits(searchSourceConfig.isTrackTotalHits())
                .version(searchSourceConfig.isVersion());
    }

}
