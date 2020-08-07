/**
 * Copyright (c) 2003-2020, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.2, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.internal.operations;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.mule.runtime.extension.api.annotation.Ignore;

import com.mulesoft.connectors.elasticsearch.api.IndexOptions;
import com.mulesoft.connectors.elasticsearch.api.SearchRequestConfiguration;
import com.mulesoft.connectors.elasticsearch.api.SearchRequestOptionalConfiguration;
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
    protected SearchRequest getSearchRequest(SearchRequestConfiguration searchRequestConfig, SearchRequestOptionalConfiguration searchRequestOptionalConfig) {
        SearchRequest searchRequest = new SearchRequest();

        if (searchRequestConfig.getIndices() != null) {
            searchRequest.indices(searchRequestConfig.getIndices().toArray(new String[0]));
        }

        if(searchRequestConfig.getSearchType() != null) {
            searchRequest.searchType(SearchType.valueOf(searchRequestConfig.getSearchType().name()));
        }

        if (searchRequestConfig.getRouting() != null) {
            // Set a routing parameter
            searchRequest.routing(searchRequestConfig.getRouting());
        }

        if (searchRequestConfig.getScrollIntervalTime() != 0) {
            searchRequest.scroll(new Scroll(TimeValue.timeValueMinutes(searchRequestConfig.getScrollIntervalTime())));
        }

        if (searchRequestConfig.getPreference() != null) {
            searchRequest.preference(searchRequestConfig.getPreference());
        }

        if (searchRequestConfig.getIndicesOptions() != null) {
            IndexOptions options = searchRequestConfig.getIndicesOptions();
            IndicesOptions indOptions = IndicesOptions.fromOptions(options.isIgnoreUnavailable(), options.isAllowNoIndices(), options.isExpandWildcardsOpen(),
                    options.isExpandWildcardsClosed(), options.isAllowAliasesToMultipleIndices(), options.isForbidClosedIndices(), options.isIgnoreAliases(),
                    options.isIgnoreThrottled());
            searchRequest.indicesOptions(indOptions);
        }

        if (searchRequestOptionalConfig != null) {
            searchRequest.allowPartialSearchResults(searchRequestOptionalConfig.isAllowPartialSearchResults());
            searchRequest.requestCache(searchRequestOptionalConfig.isRequestCache());
            searchRequest.setBatchedReduceSize(searchRequestOptionalConfig.getBatchedReduceSize());
            searchRequest.setCcsMinimizeRoundtrips(searchRequest.isCcsMinimizeRoundtrips());
            searchRequest.setMaxConcurrentShardRequests(searchRequestOptionalConfig.getMaxConcurrentShardRequests());
            searchRequest.setPreFilterShardSize(searchRequestOptionalConfig.getPreFilterShardSize());
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
            
        } else if (searchSourceConfig.getIncludeFields() != null) {
            searchSourceBuilder.fetchSource(searchSourceConfig.getIncludeFields().toArray(new String[0]), new String[0]);
            
        } else if (searchSourceConfig.getExcludeFields() != null) {
            searchSourceBuilder.fetchSource(new String[0], searchSourceConfig.getExcludeFields().toArray(new String[0]));
        }

        return searchSourceBuilder.from(searchSourceConfig.getFrom())
                .size(searchSourceConfig.getSize())
                .profile(searchSourceConfig.isProfile())
                .explain(searchSourceConfig.isExplain())
                .terminateAfter(searchSourceConfig.getTerminateAfter())
                .trackScores(searchSourceConfig.isTrackScores())
                .trackTotalHits(searchSourceConfig.isTrackTotalHits())
                .trackTotalHitsUpTo(searchSourceConfig.getTrackTotalHitsUpTo())
                .version(searchSourceConfig.isVersion())
                .minScore(searchSourceConfig.getMinimumScore())
                .seqNoAndPrimaryTerm(searchSourceConfig.isSeqNoAndPrimaryTerm());
    }

}
