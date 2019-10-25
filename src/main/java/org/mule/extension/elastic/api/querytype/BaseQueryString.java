/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.extension.elastic.api.querytype;

import java.util.Map;

import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.Placement;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 */
public class BaseQueryString {

    // Declared here because Mule SDK throwing error: Can not set **Type field to java.lang.String
    public enum QueryOperator {
        OR,
        AND
    }

    /**
     * The actual query to be parsed
     */
    @Parameter
    @Placement(order = 1)
    private String queryString;

    /**
     * List of the field name and associated field boost
     */
    @Parameter
    @Optional
    private Map<String, Float> fieldAndBoost;

    /**
     * The analyzer name used to analyze the query string
     */
    @Parameter
    @Optional
    private String analyzer;

    /**
     * Analyze the wildcards terms from the query string
     */
    @Parameter
    @Optional(defaultValue = "true")
    private boolean analyzeWildcard;

    /**
     * Enable auto generated synonyms phrase queries
     */
    @Parameter
    @Optional(defaultValue = "true")
    private boolean autoGenerateSynonymsPhraseQuery;

    /**
     * Sets the boost value of the query
     */
    @Parameter
    @Optional(defaultValue = "1.0")
    private float boost;

    /**
     * The default operator used if no explicit query operator is specified
     */
    @Parameter
    @Optional
    private QueryOperator defaultOperator;

    /**
     * Controls the number of terms fuzzy queries will expand to.
     */
    @Parameter
    @Optional(defaultValue = "50")
    private int fuzzyMaxExpansions;

    /**
     * Set the prefix length for fuzzy queries
     */
    @Parameter
    @Optional(defaultValue = "0")
    private int fuzzyPrefixLength;

    /**
     * Set to false to disable fuzzy transpositions
     */
    @Parameter
    @Optional(defaultValue = "true")
    private boolean fuzzyTranspositions;

    /**
     * If set to true will cause format based failures (like providing text to a numeric field) to be ignored.
     */
    @Parameter
    @Optional(defaultValue = "false")
    private boolean lenient;

    /**
     * Set minimum should match with possible value using integer and percentage.
     */
    @Parameter
    @Optional
    private String minimumShouldMatch;

    /**
     * A suffix to append to fields for quoted parts of the query string.
     */
    @Parameter
    @Optional
    private String quoteFieldSuffix;

    public String getQueryString() {
        return queryString;
    }

    public Map<String, Float> getFieldAndBoost() {
        return fieldAndBoost;
    }

    public String getAnalyzer() {
        return analyzer;
    }

    public boolean isAnalyzeWildcard() {
        return analyzeWildcard;
    }

    public boolean isAutoGenerateSynonymsPhraseQuery() {
        return autoGenerateSynonymsPhraseQuery;
    }

    public float getBoost() {
        return boost;
    }

    public QueryOperator getDefaultOperator() {
        return defaultOperator;
    }

    public int getFuzzyMaxExpansions() {
        return fuzzyMaxExpansions;
    }

    public int getFuzzyPrefixLength() {
        return fuzzyPrefixLength;
    }

    public boolean isFuzzyTranspositions() {
        return fuzzyTranspositions;
    }

    public boolean isLenient() {
        return lenient;
    }

    public String getMinimumShouldMatch() {
        return minimumShouldMatch;
    }

    public String getQuoteFieldSuffix() {
        return quoteFieldSuffix;
    }

}
