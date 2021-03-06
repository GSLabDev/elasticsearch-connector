/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.extension.elastic.internal.querytype;

import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 */
public class MatchAllQuery implements Query<MatchAllQueryBuilder> {

    /**
     * Sets the boost value of the query
     */
    @Parameter
    @Optional(defaultValue = "1.0")
    private float boost;

    public float getBoost() {
        return boost;
    }

    @Override
    public MatchAllQueryBuilder getQuery() {
        MatchAllQueryBuilder matchAll = QueryBuilders.matchAllQuery();
        return matchAll.boost(getBoost());
    }

}
