package com.mulesoft.connectors.elasticsearch.internal.operations;

import static org.mule.runtime.api.metadata.MediaType.APPLICATION_JSON;

import org.mule.runtime.extension.api.annotation.Ignore;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.mule.runtime.extension.api.runtime.process.CompletionCallback;
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
    protected <P, A> void responseConsumer(P response, CompletionCallback<P, A> callback) {
        String jsonResponse = null;
        try {
            jsonResponse = OBJECT_MAPPER.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            responseConsumer(ElasticsearchErrorTypes.EXECUTION, e, callback);
        }

        Result.Builder resultBuilder = Result.builder().output(jsonResponse).mediaType(APPLICATION_JSON);

        callback.success(resultBuilder.build());

    }

    @Ignore
    protected <P, A> void responseConsumer(ElasticsearchErrorTypes error, Exception e, CompletionCallback<P, A> callback) {
        callback.error(new ElasticsearchException(error, e));
    }
    
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