/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.extension.elastic.api.querytype;

import org.elasticsearch.common.unit.Fuzziness;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.Placement;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 */
public class BaseMatchQuery {

    // Declared here because Mule SDK throwing error: Can not set **Type field to java.lang.String
    public enum ZeroTermQuery {
        ALL,
        NONE
    }

    public enum QueryOperator {
        OR,
        AND
    }

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
    private ZeroTermQuery zeroTermsQuery;

    /**
     * Specify an absolute or relative document frequency
     */
    @Parameter
    @Optional(defaultValue = "0.0")
    private float cutoffFrequency;

    /**
     * Enable the auto generate synonyms phrase
     */
    @Parameter
    @Optional(defaultValue = "false")
    private boolean autoGenerateSynonymsPhraseQuery;

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

    public ZeroTermQuery getZeroTermsQuery() {
        return zeroTermsQuery;
    }

    public float getCutoffFrequency() {
        return cutoffFrequency;
    }

    public boolean isAutoGenerateSynonymsPhraseQuery() {
        return autoGenerateSynonymsPhraseQuery;
    }

}