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