/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.extension.elastic.api.querytype;

import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 */
public class MatchPhraseQuery extends BaseMatchPhraseQuery implements Query<MatchPhraseQueryBuilder> {

    @Override
    public MatchPhraseQueryBuilder getQuery() {
        MatchPhraseQueryBuilder matchPhraseQueryBuilder = QueryBuilders.matchPhraseQuery(getField(), getQueryString());

        matchPhraseQueryBuilder.boost(getBoost());

        if (getAnalyzer() != null) {
            matchPhraseQueryBuilder.analyzer(getAnalyzer());
        }

        return matchPhraseQueryBuilder;
    }
}
