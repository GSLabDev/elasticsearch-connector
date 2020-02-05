/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.internal.operations;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import org.elasticsearch.client.core.MainResponse;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mulesoft.connectors.elasticsearch.internal.connection.ElasticsearchConnection;
import com.mulesoft.connectors.elasticsearch.internal.error.ElasticsearchError;
import com.mulesoft.connectors.elasticsearch.internal.error.exception.ElasticsearchException;
import com.mulesoft.connectors.elasticsearch.internal.utils.ElasticsearchUtils;

public class InfoOperation {

    /**
     * Logging object
     */
    private static final Logger logger = LoggerFactory.getLogger(InfoOperation.class);

    /**
     * To retrieve the cluster information.
     * 
     * @param esConnection
     *            The Elasticsearch connection
     * @return MainResponse Cluster information
     * 
     */
    @MediaType(value = ANY, strict = false)
    @DisplayName("Elasticsearch - Info")
    public MainResponse info(@Connection ElasticsearchConnection esConnection) {
        MainResponse response;
        try {
            response = esConnection.getElasticsearchConnection().info(ElasticsearchUtils.getContentTypeJsonRequestOption());
        } catch (Exception e) {
            throw new ElasticsearchException(ElasticsearchError.OPERATION_FAILED, e);
        }
        logger.debug("Info response : ", response);
        return response;
    }

}
