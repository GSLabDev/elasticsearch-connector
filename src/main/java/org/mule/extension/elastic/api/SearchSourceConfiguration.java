/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.extension.elastic.api;

import java.util.List;

import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 * 
 *         Search source request configuration parameters
 */
public class SearchSourceConfiguration {

    /**
     * Retrieve search result from a certain offset
     */
    @Parameter
    @Optional(defaultValue = "0")
    private int from;

    /**
     * The number of search hits to return
     */
    @Parameter
    @Optional(defaultValue = "10")
    private int size;

    /**
     * Time allowed to finish the search operation
     */
    @Parameter
    @Optional
    private int timeout;

    /**
     * The sort order of search result using default field (_score).
     */
    @Parameter
    @Optional
    private SortOrder sortOrder;

    /**
     * Field name to sort the search result
     */
    @Parameter
    @Optional
    private String sortByFieldName;

    /**
     * Fetch the source of the search document
     */
    @Parameter
    @Optional(defaultValue = "true")
    private boolean fetchSource;

    /**
     * List of the field that are included into the document source
     */
    @Parameter
    @Optional
    private List<String> includeFields;

    /**
     * List of the field that are excluded from the document source
     */
    @Parameter
    @Optional
    private List<String> excludeFields;

    /**
     * Enable the profiling to the execution of search queries
     */
    @Parameter
    @Optional
    private boolean profile;

    /**
     * Explain the execution of search queries
     */
    @Parameter
    @Optional
    private boolean explain;

    /**
     * The maximum number of documents to collect for each shard
     */
    @Parameter
    @Optional
    private int terminateAfter;

    /**
     * Enable the search source tracking
     */
    @Parameter
    @Optional(defaultValue = "false")
    private boolean trackScores;

    /**
     * Enable the total hits tracking
     */
    @Parameter
    @Optional
    private boolean trackTotalHits;

    /**
     * Returns a version for each search hit.
     */
    @Parameter
    @Optional
    private boolean version;

    public int getFrom() {
        return from;
    }

    public int getSize() {
        return size;
    }

    public int getTimeout() {
        return timeout;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public String getSortByFieldName() {
        return sortByFieldName;
    }

    public boolean isFetchSource() {
        return fetchSource;
    }

    public List<String> getIncludeFields() {
        return includeFields;
    }

    public List<String> getExcludeFields() {
        return excludeFields;
    }

    public boolean isProfile() {
        return profile;
    }

    public boolean isExplain() {
        return explain;
    }

    public int getTerminateAfter() {
        return terminateAfter;
    }

    public boolean isTrackScores() {
        return trackScores;
    }

    public boolean isTrackTotalHits() {
        return trackTotalHits;
    }

    public boolean isVersion() {
        return version;
    }

    /**
     * Create and returns the search source builder
     * 
     * @return SearchSourceBuilder
     */
    public SearchSourceBuilder getSearchSourceBuilderOptions() {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        if (getTimeout() != 0) {
            searchSourceBuilder.timeout(TimeValue.timeValueMinutes(getTimeout()));
        }

        if (getSortOrder() != null) {
            searchSourceBuilder.sort(new ScoreSortBuilder().order(getSortOrder()));
        }

        if (getSortByFieldName() != null) {
            searchSourceBuilder.sort(new FieldSortBuilder(getSortByFieldName()).order(getSortOrder() != null ? getSortOrder() : SortOrder.DESC));
        }

        searchSourceBuilder.fetchSource(isFetchSource());

        if (getIncludeFields() != null && getExcludeFields() != null) {

            searchSourceBuilder.fetchSource(getIncludeFields().toArray(new String[0]), getExcludeFields().toArray(new String[0]));
        }

        return searchSourceBuilder.from(getFrom())
                .size(getSize())
                .profile(isProfile())
                .explain(isExplain())
                .terminateAfter(getTerminateAfter())
                .trackScores(isTrackScores())
                .trackTotalHits(isTrackTotalHits())
                .version(isVersion());
    }
}
