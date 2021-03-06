/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.extension.elastic.internal.querytype;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.search.MatchQuery.ZeroTermsQuery;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.Placement;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 */
public class MatchQuery extends BaseMatchQuery implements Query<MatchQueryBuilder> {

    /**
     * Restrict the search request to field
     */
    @Parameter
    @Placement(order = 2)
    private String field;

    public String getField() {
        return field;
    }

    @Override
    public MatchQueryBuilder getQuery() {
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(getField(), getSearchString());

        if (getFuzziness() != null) {
            matchQueryBuilder.fuzziness(getFuzziness());
        }

        if (getOperator() != null) {
            matchQueryBuilder.operator(Operator.fromString(getOperator().name()));
        }

        if (getZeroTermsQuery() != null) {
            matchQueryBuilder.zeroTermsQuery(ZeroTermsQuery.valueOf(getZeroTermsQuery().name()));
        }

        return matchQueryBuilder.autoGenerateSynonymsPhraseQuery(isAutoGenerateSynonymsPhraseQuery())
                .cutoffFrequency(getCutoffFrequency())
                .lenient(isLenient())
                .fuzzyTranspositions(isFuzzyTranspositions())
                .prefixLength(getPrefixLength())
                .maxExpansions(getMaxExpansions());
    }

}
