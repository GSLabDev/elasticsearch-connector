/**
 * Copyright (c) 2003-2020, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.2, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.internal.connection.provider;

import java.io.IOException;

import org.mule.runtime.api.connection.CachedConnectionProvider;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mulesoft.connectors.elasticsearch.internal.connection.ElasticsearchConnection;
import com.mulesoft.connectors.elasticsearch.internal.utils.ElasticsearchUtils;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 */
public abstract class ElasticsearchBaseConnectionProvider implements CachedConnectionProvider<ElasticsearchConnection> {

    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchBaseConnectionProvider.class);

    @Parameter
    @DisplayName("Host")
    @Placement(order = 1)
    @Summary("ElasticSearch instance host IP or DNS name")
    private String host;

    @Parameter
    @DisplayName("Port")
    @Placement(order = 2)
    @Optional(defaultValue = "9200")
    @Summary("ElasticSearch instance port")
    private int port;

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    @Override
    public void disconnect(ElasticsearchConnection connection) {
        try {
            connection.invalidate();
        } catch (IOException e) {
            logger.error("Error while disconnecting [" + getHost() + ":" + getPort() + "]: " + e.getMessage(), e);
        }
    }

    @Override
    public ConnectionValidationResult validate(ElasticsearchConnection connection) {
        try {
            if (connection.getElasticsearchConnection().ping(ElasticsearchUtils.getAcceptJsonRequestOption())) {
                return ConnectionValidationResult.success();
            }
            return ConnectionValidationResult.failure("Ping to ElasticSearch instance is unsuccessful", new IOException("Unable to connect to " + getHost() + ":" + getPort()));
        } catch (IOException e) {
            return ConnectionValidationResult.failure(e.getMessage(), e);
        }
    }

}
