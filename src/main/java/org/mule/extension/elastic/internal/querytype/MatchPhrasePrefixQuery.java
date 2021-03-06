/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.extension.elastic.internal.querytype;

import org.elasticsearch.index.query.MatchPhrasePrefixQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 */
public class MatchPhrasePrefixQuery extends BaseMatchPhraseQuery implements Query<MatchPhrasePrefixQueryBuilder> {

    /**
     * Number of suffixes the last term will be expanded
     */
    @Parameter
    @Optional(defaultValue = "50")
    private int maxExpansions;

    public int getMaxExpansions() {
        return maxExpansions;
    }

    @Override
    public MatchPhrasePrefixQueryBuilder getQuery() {

        MatchPhrasePrefixQueryBuilder matchPhrasePrefixQueryBuilder = QueryBuilders.matchPhrasePrefixQuery(getField(), getQueryString());

        matchPhrasePrefixQueryBuilder.boost(getBoost());

        if (getAnalyzer() != null) {
            matchPhrasePrefixQueryBuilder.analyzer(getAnalyzer());
        }

        return matchPhrasePrefixQueryBuilder.maxExpansions(getMaxExpansions());
    }

}
