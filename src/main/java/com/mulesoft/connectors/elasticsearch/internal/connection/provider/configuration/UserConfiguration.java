/**
 * Copyright (c) 2003-2020, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.2, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.internal.connection.provider.configuration;

import java.util.concurrent.TimeUnit;

import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Password;
import org.mule.runtime.extension.api.annotation.param.display.Placement;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 * 
 *         User authentication configuration parameters
 */
public class UserConfiguration {

    /**
     * Elasticsearch instance username. Keep this blank for anonymous user.
     */
    @Parameter
    @Optional
    @DisplayName("User Name")
    private String userName;

    /**
     * Elasticsearch instance password. Keep this blank for anonymous user.
     */
    @Parameter
    @Optional
    @DisplayName("Password")
    @Password
    private String password;

    /**
     * Elasticsearch connect timeout
     */
    @DisplayName("Connect Timeout")
    @Parameter
    @Placement(tab = "Advanced")
    @Optional(defaultValue = "1")
    int connectTimeout;
    
    /**
     * Elasticsearch socket timeout
     */
    @DisplayName("Socket Timeout")
    @Parameter
    @Placement(tab = "Advanced")
    @Optional(defaultValue = "30")
    int socketTimeout;
    
    @DisplayName("Time Unit")
    @Parameter
    @Placement(tab = "Advanced")
    @Optional(defaultValue = "SECONDS")
    TimeUnit timeUnit;
    
    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
    
    public int getConnectTimeout() {
		return connectTimeout;
	}

	public int getSocketTimeout() {
		return socketTimeout;
	}

	public TimeUnit getTimeUnit() {
        return timeUnit;
    }
    
}