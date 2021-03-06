/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.extension.elastic.internal.querytype;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 */
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.SimpleQueryStringBuilder;
import org.elasticsearch.index.query.SimpleQueryStringFlag;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;

public class SimpleQueryString extends BaseQueryString implements Query<SimpleQueryStringBuilder> {

    /**
     * use type org.elasticsearch.index.query.SimpleQueryStringFlag instead of SimpleQueryFlag
     * 
     * Declared here because Mule SDK throwing error: Can not set **Type field to java.lang.String
     */

    public enum SimpleQueryFlag {
        ALL,
        AND,
        ESCAPE,
        FUZZY,
        NEAR,
        NONE,
        NOT,
        OR,
        PHRASE,
        PRECEDENCE,
        PREFIX,
        SLOP,
        WHITESPACE
    }

    /**
     * Specify which parsing flag should be enabled
     */
    @Parameter
    @Optional
    SimpleQueryFlag simpleQueryStringFlag;

    public SimpleQueryFlag getSimpleQueryStringFlag() {
        return simpleQueryStringFlag;
    }

    @Override
    public SimpleQueryStringBuilder getQuery() {
        SimpleQueryStringBuilder simpleQueryStringBuilder = QueryBuilders.simpleQueryStringQuery(getQueryString());

        if (getFieldAndBoost() != null) {
            simpleQueryStringBuilder.fields(getFieldAndBoost());
        }

        if (getSimpleQueryStringFlag() != null) {
            simpleQueryStringBuilder.flags(SimpleQueryStringFlag.valueOf(getSimpleQueryStringFlag().name()));
        }

        if (getAnalyzer() != null) {
            simpleQueryStringBuilder.analyzer(getAnalyzer());
        }

        if (getDefaultOperator() != null) {
            simpleQueryStringBuilder.defaultOperator(Operator.fromString(getDefaultOperator().name()));
        }

        if (getMinimumShouldMatch() != null) {
            simpleQueryStringBuilder.minimumShouldMatch(getMinimumShouldMatch());
        }

        if (getQuoteFieldSuffix() != null) {
            simpleQueryStringBuilder.quoteFieldSuffix(getQuoteFieldSuffix());
        }

        return simpleQueryStringBuilder.analyzeWildcard(isAnalyzeWildcard())
                .autoGenerateSynonymsPhraseQuery(isAutoGenerateSynonymsPhraseQuery())
                .fuzzyMaxExpansions(getFuzzyMaxExpansions())
                .fuzzyPrefixLength(getFuzzyPrefixLength())
                .fuzzyTranspositions(isFuzzyTranspositions())
                .lenient(isLenient())
                .boost(getBoost());
    }
}
