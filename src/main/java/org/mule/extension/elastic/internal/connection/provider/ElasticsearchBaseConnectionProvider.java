/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.extension.elastic.internal.connection.provider;

import java.io.IOException;

import org.apache.http.HttpHeaders;
import org.apache.http.message.BasicHeader;
import org.mule.extension.elastic.internal.connection.ElasticsearchConnection;
import org.mule.runtime.api.connection.CachedConnectionProvider;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            if (connection.getElasticsearchConnection().ping(new BasicHeader(HttpHeaders.ACCEPT, "application/json"))) {
                return ConnectionValidationResult.success();
            }
            return ConnectionValidationResult.failure("Ping to ElasticSearch instance is unsuccessful", new IOException("Unable to connect to " + getHost() + ":" + getPort()));
        } catch (IOException e) {
            return ConnectionValidationResult.failure(e.getMessage(), e);
        }
    }

}
