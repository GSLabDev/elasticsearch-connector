/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.api;

import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;

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
        DEFAULT,
        DFS_QUERY_THEN_FETCH,
        QUERY_THEN_FETCH;
    }

    /**
     * Restricts the search request to an index
     */
    @Parameter
    @Optional
    private String index;

    /**
     * The type of the search operation to perform
     */
    @Parameter
    @Optional
    private SearchType searchType;

    /**
     * Limits the search request to a type
     */
    @Parameter
    @Optional
    private String type;

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
    int scrollIntervalTime;

    public String getIndex() {
        return index;
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public String getType() {
        return type;
    }

    public String getRouting() {
        return routing;
    }

    public int getScrollIntervalTime() {
        return scrollIntervalTime;
    }

}
