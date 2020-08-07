/**
 * Copyright (c) 2003-2020, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.2, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.internal.operations;

import org.mule.runtime.extension.api.annotation.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.elasticsearch.internal.error.ElasticsearchErrorTypes;
import com.mulesoft.connectors.elasticsearch.internal.error.exception.ElasticsearchException;

/**
 * 
 * Base class for Elasticsearch operations
 *
 */
public class ElasticsearchOperations {

    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchOperations.class);
    static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    
    @Ignore
    protected <P> String getJsonResponse(P response) {
        String jsonResponse = null;
        try {
            jsonResponse = OBJECT_MAPPER.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            throw new ElasticsearchException(ElasticsearchErrorTypes.EXECUTION, e);
        }
        return jsonResponse;
    }

}