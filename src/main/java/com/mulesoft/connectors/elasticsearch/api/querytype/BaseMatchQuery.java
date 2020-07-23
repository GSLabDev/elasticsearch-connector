/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.api.querytype;

import org.elasticsearch.common.unit.Fuzziness;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.Placement;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 */
public class BaseMatchQuery {

    /**
     * Search text string
     */
    @Parameter
    @Placement(order = 1)
    private String searchString;

    /**
     * Enable fuzzy matching on the match query
     */
    @Parameter
    @Optional
    private Fuzziness fuzziness;

    /**
     * Fuzzy Rewrite
     */
    @Parameter
    @Optional
    private String fuzzyRewrite;

    /**
     * Allow the fuzzy transpositions match
     */
    @Parameter
    @Optional(defaultValue = "true")
    private boolean fuzzyTranspositions;

    /**
     * Prefix length option on the match query
     */
    @Parameter
    @Optional(defaultValue = "0")
    private int prefixLength;

    /**
     * Max expansion to control the fuzzy process of the query
     */
    @Parameter
    @Optional(defaultValue = "50")
    private int maxExpansions;

    /**
     * Set the match query operator
     */
    @Parameter
    @Optional
    private QueryOperator operator;

    /**
     * Ignore exceptions caused by data-type mismatches
     */
    @Parameter
    @Optional(defaultValue = "false")
    private boolean lenient;

    /**
     * Set the zero terms query option
     */
    @Parameter
    @Optional
    private ZeroTermsQuery zeroTermsQuery;

    /**
     * The analyzer to use.
     */
    @Parameter
    @Optional
    private String analyzer;

    /**
     * Enable the auto generate synonyms phrase
     */
    @Parameter
    @Optional(defaultValue = "false")
    private boolean autoGenerateSynonymsPhraseQuery;

    /**
     * Sets the boost value of the query
     */
    @Parameter
    @Optional(defaultValue = "1.0")
    private float boost;

    /**
     * Set minimum should match with possible value using integer and percentage.
     */
    @Parameter
    @Optional
    private String minimumShouldMatch;
    
    public BaseMatchQuery() {
        // This default constructor makes the class DataWeave compatible.
    }

    public String getSearchString() {
        return searchString;
    }

    public Fuzziness getFuzziness() {
        return fuzziness;
    }

    public boolean isFuzzyTranspositions() {
        return fuzzyTranspositions;
    }

    public int getPrefixLength() {
        return prefixLength;
    }

    public int getMaxExpansions() {
        return maxExpansions;
    }

    public QueryOperator getOperator() {
        return operator;
    }

    public boolean isLenient() {
        return lenient;
    }

    public ZeroTermsQuery getZeroTermsQuery() {
        return zeroTermsQuery;
    }

    public boolean isAutoGenerateSynonymsPhraseQuery() {
        return autoGenerateSynonymsPhraseQuery;
    }

    public String getAnalyzer() {
        return analyzer;
    }

    public String getFuzzyRewrite() {
        return fuzzyRewrite;
    }

    public String getMinimumShouldMatch() {
        return minimumShouldMatch;
    }

    public float getBoost() {
        return boost;
    }
}