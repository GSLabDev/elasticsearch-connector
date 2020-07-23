/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.api;

import java.util.List;

import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.Summary;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 * 
 *         Search request configuration parameters
 *
 */
public class SearchRequestConfiguration {

    /**
     * Search query method types
     */
    public enum SearchType {
        DFS_QUERY_THEN_FETCH,
        QUERY_THEN_FETCH;
    }

    /**
     * Restricts the search request to one or more indices.
     */
    @Parameter
    @Optional
    private List<String> indices;

    /**
     * The type of the search operation to perform
     */
    @Parameter
    @Optional
    private SearchType searchType;

    /**
     * Search the document using routing
     */
    @Parameter
    @Optional
    private String routing;

    /**
     * Scroll interval time keep the search context alive(minutes). If value is non-zero, scroll search is initiated.
     */
    @Parameter
    @Optional(defaultValue = "0")
    @Summary("Time interval (in minutes) to keep the search context alive.")
    private int scrollIntervalTime;

    /**
     * The preference to execute the search. Use the preference parameter e.g. to execute the search to prefer local shards. The default is to randomize across shards.
     */
    @Parameter
    @Optional
    private String preference;

    /**
     * It controls how unavailable indices are resolved and how wildcard expressions are expanded
     */
    @Parameter
    @Optional
    private IndexOptions indicesOptions;
    
    public SearchRequestConfiguration() {
        // This default constructor makes the class DataWeave compatible.
    }

    public List<String> getIndices() {
        return indices;
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public String getRouting() {
        return routing;
    }

    public int getScrollIntervalTime() {
        return scrollIntervalTime;
    }

    public String getPreference() {
        return preference;
    }

    public IndexOptions getIndicesOptions() {
        return indicesOptions;
    }

}
