/**
 * Copyright (c) 2003-2020, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.2, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.api.querytype;

import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Parameter;

public class QueryConfiguration {

    /**
     * Different types of Elasticsearch query configuration
     */
    @Parameter
    @Expression(ExpressionSupport.NOT_SUPPORTED)
    private Query queryType;

    public Query getQueryType() {
        return queryType;
    }

}