/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.api.querytype;

import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 */
public class QueryStringQuery extends BaseQueryString implements Query {

    /**
     * Allow first wildcard (* or ?) in query string
     */
    @Parameter
    @Optional(defaultValue = "true")
    private boolean allowLeadingWildcard;

    /**
     * The default field for query terms if no prefix field is specified.
     */
    @Parameter
    @Optional
    private String defaultField;

    /**
     * Set to true to enable position increments in result queries.
     */
    @Parameter
    @Optional(defaultValue = "true")
    private boolean enablePositionIncrements;

    /**
     * Allow escape in query string
     */
    @Parameter
    @Optional(defaultValue = "true")
    private boolean escape;

    /**
     * Sets the default slop for phrases. If zero, then exact phrase matches are required.
     */
    @Parameter
    @Optional(defaultValue = "0")
    private int phraseSlop;

    /**
     * The disjunction max tie breaker for multi fields
     */
    @Parameter
    @Optional(defaultValue = "0")
    private float tieBreaker;

    public boolean isAllowLeadingWildcard() {
        return allowLeadingWildcard;
    }

    public String getDefaultField() {
        return defaultField;
    }

    public boolean isEnablePositionIncrements() {
        return enablePositionIncrements;
    }

    public boolean isEscape() {
        return escape;
    }

    public int getPhraseSlop() {
        return phraseSlop;
    }

    public float getTieBreaker() {
        return tieBreaker;
    }

    @Override
    public QueryStringQueryBuilder getQuery() {
        QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders.queryStringQuery(getQueryString());

        if (getFieldAndBoost() != null) {
            queryStringQueryBuilder.fields(getFieldAndBoost());
        }

        if (getDefaultField() != null && getFieldAndBoost() == null) {
            queryStringQueryBuilder.defaultField(getDefaultField());
        }

        if (getAnalyzer() != null) {
            queryStringQueryBuilder.analyzer(getAnalyzer());
        }

        if (getDefaultOperator() != null) {
            queryStringQueryBuilder.defaultOperator(Operator.fromString(getDefaultOperator().name()));
        }

        if (getMinimumShouldMatch() != null) {
            queryStringQueryBuilder.minimumShouldMatch(getMinimumShouldMatch());
        }

        if (getQuoteFieldSuffix() != null) {
            queryStringQueryBuilder.quoteFieldSuffix(getQuoteFieldSuffix());
        }

        return queryStringQueryBuilder.autoGenerateSynonymsPhraseQuery(isAutoGenerateSynonymsPhraseQuery())
                .allowLeadingWildcard(isAllowLeadingWildcard())
                .analyzeWildcard(isAnalyzeWildcard())
                .enablePositionIncrements(isEnablePositionIncrements())
                .escape(isEscape())
                .phraseSlop(getPhraseSlop())
                .tieBreaker(getTieBreaker())
                .fuzzyMaxExpansions(getFuzzyMaxExpansions())
                .fuzzyPrefixLength(getFuzzyPrefixLength())
                .fuzzyTranspositions(isFuzzyTranspositions())
                .lenient(isLenient())
                .boost(getBoost());
    }

}
