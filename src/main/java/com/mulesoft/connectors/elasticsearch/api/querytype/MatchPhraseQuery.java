/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.api.querytype;

import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;

/**
 * Match query is a query that analyzes the text and constructs a phrase query as the result of the analysis.
 */
public class MatchPhraseQuery extends BaseMatchPhraseQuery implements Query {

    /**
     * Query to use in case no query terms are available, e.g. after analysis removed them. 
     */
    @Parameter
    @Optional
    private ZeroTermsQuery zeroTermsQuery;

    public ZeroTermsQuery getZeroTermsQuery() {
        return zeroTermsQuery;
    }

    @Override
    public MatchPhraseQueryBuilder getQuery() {
        MatchPhraseQueryBuilder matchPhraseQueryBuilder = QueryBuilders.matchPhraseQuery(getField(), getQueryString());

        matchPhraseQueryBuilder.boost(getBoost());
        matchPhraseQueryBuilder.slop(getSlop());

        if (getAnalyzer() != null) {
            matchPhraseQueryBuilder.analyzer(getAnalyzer());
        }

        if (getZeroTermsQuery() != null) {
            matchPhraseQueryBuilder.zeroTermsQuery(org.elasticsearch.index.search.MatchQuery.ZeroTermsQuery.valueOf(getZeroTermsQuery().name()));
        }

        return matchPhraseQueryBuilder;
    }
}
