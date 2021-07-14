/**
 * Copyright (c) 2003-2020, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.2, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.internal.operation;

import org.elasticsearch.client.core.MainResponse;
import org.mule.runtime.api.util.MultiMap;
import org.mule.runtime.extension.api.annotation.metadata.OutputResolver;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.runtime.operation.Result;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;

import com.mulesoft.connectors.elasticsearch.api.ResponseAttributes;
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

    private final int SUCCESS_200 = 200;
    
    /**
     * Retrieves the cluster information.
     * 
     * @param esConnection
     *            The Elasticsearch connection
     * @return MainResponse as JSON String
     */
    @MediaType(MediaType.APPLICATION_JSON)
    @DisplayName("Elasticsearch - Info")
    @OutputResolver(output = MainResponseOutputMetadataResolver.class)
    public Result<InputStream, ResponseAttributes> info(@Connection ElasticsearchConnection esConnection) {
        String result = null;
        InputStream inputStreamResponse = null;
        ResponseAttributes attributes = null;
        try {
            MainResponse response = esConnection.getElasticsearchConnection().info(ElasticsearchUtils.getContentTypeJsonRequestOption());
            result = getJsonResponse(response);
            inputStreamResponse = new ByteArrayInputStream(result.getBytes(Charset.forName("UTF-8")));
            attributes = new ResponseAttributes(SUCCESS_200, new MultiMap<>());
            logger.info("Cluster information: " + result);
        } catch (Exception e) {
            logger.error(e);
            throw new ElasticsearchException(ElasticsearchErrorTypes.OPERATION_FAILED, e);
        }
        return Result.<InputStream, ResponseAttributes>builder() 
                .output(inputStreamResponse)
                .attributes(attributes)
                .build();
    }
}
