/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.api.querytype;

import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.Placement;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 */
public class BaseMatchPhraseQuery {

    /**
     * Restrict the search request to field
     */
    @Parameter
    @Placement(order = 1)
    private String field;

    /**
     * Search text string
     */
    @Parameter
    @Placement(order = 2)
    private String queryString;

    /**
     * Sets the boost value of the query
     */
    @Parameter
    @Optional(defaultValue = "1.0")
    private float boost;

    /**
     * The analyzer name used to analyze the query string
     */
    @Parameter
    @Optional
    private String analyzer;

    /**
     * A slop factor for phrase queries
     */
    @Parameter
    @Optional
    private int slop;

    public String getField() {
        return field;
    }

    public String getQueryString() {
        return queryString;
    }

    public float getBoost() {
        return boost;
    }

    public String getAnalyzer() {
        return analyzer;
    }

    public int getSlop() {
        return slop;
    }
}
