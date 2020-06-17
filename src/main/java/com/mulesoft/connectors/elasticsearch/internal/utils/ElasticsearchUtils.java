/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
/**
 *
 */
package com.mulesoft.connectors.elasticsearch.internal.utils;

import java.io.IOException;

import java.util.function.Consumer;
import org.apache.log4j.Logger;
import org.elasticsearch.client.RequestOptions;
import org.mule.runtime.core.api.util.IOUtils;

import com.mulesoft.connectors.elasticsearch.internal.error.ElasticsearchErrorTypes;
import com.mulesoft.connectors.elasticsearch.internal.error.exception.ElasticsearchException;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 * 
 *         ElasticSearch util functions
 */
public class ElasticsearchUtils {
    
    /**
     * Logging object
     */
    private static final Logger logger = Logger.getLogger(ElasticsearchUtils.class.getName());

    private ElasticsearchUtils() {
    }

    public static RequestOptions getContentTypeJsonRequestOption() {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder().addHeader("Content-Type", "application/json");
        return builder.build();
    }
    
    public static RequestOptions getAcceptJsonRequestOption() {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder().addHeader("Accept", "application/json");
        return builder.build();
    }

    public static <T> void ifPresent(T value, Consumer<T> consumer) {
        if (value != null) {
            consumer.accept(value);
        }
    }

    public static String readFileToString(String filePath) throws IOException {
        try {
            return IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath));
        } catch (NullPointerException e) {
            logger.error(String.format("File %s is not found.", filePath));
            throw new ElasticsearchException(ElasticsearchErrorTypes.EXECUTION, e);
        }   
    }

}
