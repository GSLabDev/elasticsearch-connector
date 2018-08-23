package org.mule.extension.elastic.internal.operations;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.mule.extension.elastic.api.SearchRequestConfiguration;
import org.mule.extension.elastic.api.SearchSourceConfiguration;

public class BaseSearchOperation {

    /**
     * SearchRequest Builder
     * 
     * @param searchRequestConfig
     *            Search Request configuration parameters
     * @return SearchRequest
     */
    public SearchRequest getSearchRequest(SearchRequestConfiguration searchRequestConfig) {
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
    public SearchSourceBuilder getSearchSourceBuilderOptions(SearchSourceConfiguration searchSourceConfig) {

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
