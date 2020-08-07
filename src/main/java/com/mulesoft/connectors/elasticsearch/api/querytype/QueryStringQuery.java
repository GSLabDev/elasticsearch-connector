/**
 * Copyright (c) 2003-2020, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.2, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.api.querytype;

import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;

/**
 * A query that parses a query string and runs it. There are two modes that this operates. The first, when no field is added (using field(String), will run the query once and non
 * prefixed fields will use the defaultField(String) set. The second, when one or more fields are added (using field(String)), will run the parsed query against the provided
 * fields, and combine them using Dismax.
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
     * Fuzzy rewrite.
     */
    @Parameter
    @Optional
    private String fuzzyRewrite;

    /**
     * Protects against too-difficult regular expression queries.
     */
    @Parameter
    @Optional
    private int maxDeterminizedStates;

    /**
     * Sets the default slop for phrases. If zero, then exact phrase matches are required.
     */
    @Parameter
    @Optional(defaultValue = "0")
    private int phraseSlop;

    /**
     * The optional analyzer used to analyze the query string for phrase searches.
     */
    @Parameter
    @Optional
    private String quoteAnalyzer;

    /**
     * The optional analyzer used to analyze the query string for phrase searches.
     */
    @Parameter
    @Optional
    private String rewrite;

    /**
     * The disjunction max tie breaker for multi fields
     */
    @Parameter
    @Optional(defaultValue = "0")
    private float tieBreaker;

    /**
     * In case of date field, we can adjust the from/to fields using a timezone
     */
    @Parameter
    @Optional
    private String timeZone;
    
    public QueryStringQuery() {
        // This default constructor makes the class DataWeave compatible.
    }

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

    public String getFuzzyRewrite() {
        return fuzzyRewrite;
    }

    public int getMaxDeterminizedStates() {
        return maxDeterminizedStates;
    }

    public String getQuoteAnalyzer() {
        return quoteAnalyzer;
    }

    public String getRewrite() {
        return rewrite;
    }

    public String getTimeZone() {
        return timeZone;
    }

    @Override
    public QueryStringQueryBuilder getQuery() {
        QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders.queryStringQuery(getQueryString());

        if (getFieldsAndBoost() != null) {
            queryStringQueryBuilder.fields(getFieldsAndBoost());
        }

        if (getDefaultField() != null && getFieldsAndBoost() == null) {
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

        if (getMaxDeterminizedStates() != 0) {
            queryStringQueryBuilder.maxDeterminizedStates(getMaxDeterminizedStates());
        }

        if (getFuzzyRewrite() != null) {
            queryStringQueryBuilder.fuzzyRewrite(getFuzzyRewrite());
        }

        if (getRewrite() != null) {
            queryStringQueryBuilder.rewrite(getRewrite());
        }

        if (getQuoteAnalyzer() != null) {
            queryStringQueryBuilder.quoteAnalyzer(getQuoteAnalyzer());
        }

        if (getQuoteFieldSuffix() != null) {
            queryStringQueryBuilder.quoteFieldSuffix(getQuoteFieldSuffix());
        }
        
        if (getTimeZone() != null) {
            queryStringQueryBuilder.timeZone(getTimeZone());
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
