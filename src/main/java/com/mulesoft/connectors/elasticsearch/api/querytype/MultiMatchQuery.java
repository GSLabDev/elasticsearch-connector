/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.api.querytype;

import java.util.List;

import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder.Type;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;

/**
 * Same as MatchQuery but supports multiple fields.
 */
public class MultiMatchQuery extends BaseMatchQuery implements Query {

    // Declared here because Mule SDK throwing error: Can not set **Type field to java.lang.String
    public enum MultiMatchQueryType {
        BEST_FIELDS,
        CROSS_FIELDS,
        MOST_FIELDS,
        PHRASE,
        PHRASE_PREFIX,
        BOOL_PREFIX
    }

    /**
     * Restrict the search request to list of field
     */
    @Parameter
    @Placement(order = 2)
    private List<String> fields;

    /**
     * The phrase slop if evaluated to a phrase query type.
     */
    @Parameter
    @Optional
    private int slop;

    /**
     * Type of the multi match query is executed internally
     */
    @Parameter
    @Optional
    @DisplayName("Type")
    private MultiMatchQueryType type;

    /**
     * The tie breaker parameter used to select field in a group of field based on score
     * 
     * 0.0 - Take the single best score out of multiple field
     * 
     * 1.0 - Add together the scores of the multiple field
     * 
     * 0.0 < n < 1.0 - Take the single best score plus tie_breaker multiplied by each of the scores from other matching fields.
     */
    @Parameter
    @Optional(defaultValue = "0.0")
    private float tieBreaker;

    public List<String> getFields() {
        return fields;
    }

    public int getSlop() {
        return slop;
    }

    public MultiMatchQueryType getType() {
        return type;
    }

    public float getTieBreaker() {
        return tieBreaker;
    }

    @Override
    public MultiMatchQueryBuilder getQuery() {

        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(getSearchString(), getFields().toArray(new String[0]));
        multiMatchQueryBuilder.boost(getBoost());
        multiMatchQueryBuilder.slop(getSlop());

        if (getFuzziness() != null) {
            multiMatchQueryBuilder.fuzziness(getFuzziness());
        }

        if (getOperator() != null) {
            multiMatchQueryBuilder.operator(Operator.fromString(getOperator().name()));
        }

        if (getZeroTermsQuery() != null) {
            multiMatchQueryBuilder.zeroTermsQuery(org.elasticsearch.index.search.MatchQuery.ZeroTermsQuery.valueOf(getZeroTermsQuery().name()));
        }

        if (getType() != null) {
            multiMatchQueryBuilder.type(Type.valueOf(getType().name()));
        }

        if (getMinimumShouldMatch() != null) {
            multiMatchQueryBuilder.minimumShouldMatch(getMinimumShouldMatch());
        }

        if (getAnalyzer() != null) {
            multiMatchQueryBuilder.analyzer(getAnalyzer());
        }

        if (getFuzzyRewrite() != null) {
            multiMatchQueryBuilder.fuzzyRewrite(getFuzzyRewrite());
        }

        return multiMatchQueryBuilder.fuzzyTranspositions(isFuzzyTranspositions())
                .prefixLength(getPrefixLength())
                .maxExpansions(getMaxExpansions())
                .lenient(isLenient())
                .autoGenerateSynonymsPhraseQuery(isAutoGenerateSynonymsPhraseQuery())
                .tieBreaker(getTieBreaker());
    }

}
