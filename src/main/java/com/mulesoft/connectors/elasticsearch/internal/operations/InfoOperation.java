/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.internal.operations;

import org.elasticsearch.client.core.MainResponse;
import org.mule.runtime.extension.api.annotation.metadata.OutputResolver;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.apache.log4j.Logger;

import com.mulesoft.connectors.elasticsearch.internal.connection.ElasticsearchConnection;
import com.mulesoft.connectors.elasticsearch.internal.error.ElasticsearchErrorTypes;
import com.mulesoft.connectors.elasticsearch.internal.error.exception.ElasticsearchException;
import com.mulesoft.connectors.elasticsearch.internal.metadata.MainResponseOutputMetadataResolver;
import com.mulesoft.connectors.elasticsearch.internal.utils.ElasticsearchUtils;

public class InfoOperation extends ElasticsearchOperations {

    /**
     * Logging object
     */
    private static final Logger logger = Logger.getLogger(InfoOperation.class.getName());

    /**
     * Retrieves the cluster information.
     * 
     * @param esConnection
     *            The Elasticsearch connection
     * @return MainResponse as JSON String
     */
    @MediaType(value = MediaType.APPLICATION_JSON, strict = false)
    @DisplayName("Elasticsearch - Info")
    @OutputResolver(output = MainResponseOutputMetadataResolver.class)
    public String info(@Connection ElasticsearchConnection esConnection) {
        String result = null;
        try {
            MainResponse response = esConnection.getElasticsearchConnection().info(ElasticsearchUtils.getContentTypeJsonRequestOption());
            result = getJsonResponse(response);
        } catch (Exception e) {
            logger.error(e);
            throw new ElasticsearchException(ElasticsearchErrorTypes.OPERATION_FAILED, e);
        }
        return result;
    }
}
