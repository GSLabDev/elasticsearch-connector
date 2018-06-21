/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.extension.elastic.api.querytype;

import org.elasticsearch.index.query.CommonTermsQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.Placement;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 */
public class CommonTermsQuery implements Query<CommonTermsQueryBuilder> {

    // Declared here because Mule SDK throwing error: Can not set **Type field to java.lang.String
    public enum QueryOperator {
        OR,
        AND
    }

    /**
     * Restrict the search request to field
     */
    @Parameter
    @Placement(order = 1)
    private String fieldName;

    /**
     * Search text string
     */
    @Parameter
    @Placement(order = 2)
    private String queryString;

    /**
     * The analyzer name used to analyze the query string
     */
    @Parameter
    @Optional
    String analyzer;

    /**
     * Sets the boost value of the query
     */
    @Parameter
    @Optional(defaultValue = "0")
    private float boost;

    /**
     * Specify an absolute or relative document frequency
     */
    @Parameter
    @Optional(defaultValue = "0.0")
    private float cutoffFrequency;

    /**
     * Set minimum should match with possible value using integer and percentage.
     */
    @Parameter
    @Optional
    private String highFreqMinimumShouldMatch;

    /**
     * High frequency query operator
     */
    @Parameter
    @Optional
    private QueryOperator highFreqOperator;

    /**
     * Set minimum should match with possible value using integer and percentage.
     * 
     * @see <a href="http://google.com">Minimum Should Matchedit</a>
     */
    @Parameter
    @Optional
    private String lowFreqMinimumShouldMatch;

    /**
     * Low frequency query operator
     */
    @Parameter
    @Optional
    private QueryOperator lowFreqOperator;

    public String getFieldName() {
        return fieldName;
    }

    public String getQueryString() {
        return queryString;
    }

    public String getAnalyzer() {
        return analyzer;
    }

    public float getBoost() {
        return boost;
    }

    public float getCutoffFrequency() {
        return cutoffFrequency;
    }

    public String getHighFreqMinimumShouldMatch() {
        return highFreqMinimumShouldMatch;
    }

    public QueryOperator getHighFreqOperator() {
        return highFreqOperator;
    }

    public String getLowFreqMinimumShouldMatch() {
        return lowFreqMinimumShouldMatch;
    }

    public QueryOperator getLowFreqOperator() {
        return lowFreqOperator;
    }

    @Override
    public CommonTermsQueryBuilder getQuery() {
        CommonTermsQueryBuilder commonTermsQueryBuilder = QueryBuilders.commonTermsQuery(getFieldName(), getQueryString());

        if (getAnalyzer() != null) {
            commonTermsQueryBuilder.analyzer(getAnalyzer());
        }

        if (Float.compare(getBoost(), 0.0F) != 0) {
            commonTermsQueryBuilder.boost(getBoost());
        }

        if (getHighFreqMinimumShouldMatch() != null) {
            commonTermsQueryBuilder.highFreqMinimumShouldMatch(getHighFreqMinimumShouldMatch());
        }

        if (getHighFreqOperator() != null) {
            commonTermsQueryBuilder.highFreqOperator(Operator.fromString(getHighFreqOperator().name()));
        }

        if (getLowFreqMinimumShouldMatch() != null) {
            commonTermsQueryBuilder.lowFreqMinimumShouldMatch(getLowFreqMinimumShouldMatch());
        }

        if (getLowFreqOperator() != null) {
            commonTermsQueryBuilder.highFreqOperator(Operator.fromString(getLowFreqOperator().name()));
        }

        return commonTermsQueryBuilder.cutoffFrequency(getCutoffFrequency());
    }

}
