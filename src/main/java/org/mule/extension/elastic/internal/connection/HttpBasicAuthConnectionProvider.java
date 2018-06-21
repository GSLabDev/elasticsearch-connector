/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.extension.elastic.internal.connection;

import org.mule.extension.elastic.api.UserConfiguration;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.dsl.xml.ParameterDsl;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 * 
 *         Provides HTTP connection with basic user-name and password Authentication
 */
@Alias("http-basic-auth-connection")
@DisplayName("HTTP With Basic Authentication")
public class HttpBasicAuthConnectionProvider extends ElasticsearchBaseConnectionProvider {

    @ParameterGroup(name = "User Credentials")
    @ParameterDsl(allowInlineDefinition = true)
    private UserConfiguration userConfiguration;

    @Override
    public ElasticsearchConnection connect() throws ConnectionException {
        return new ElasticsearchConnection(getHost(), getPort(), userConfiguration.getUserName(), userConfiguration.getPassword());
    }
}
