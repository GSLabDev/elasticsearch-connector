/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.extension.elastic.internal.connection;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 * 
 *         Provides HTTP ElasticSearch connection
 */
@DisplayName("HTTP Connection")
@Alias("http-connection")
public class HttpConnectionProvider extends ElasticsearchBaseConnectionProvider {

    @Override
    public ElasticsearchConnection connect() throws ConnectionException {
        return new ElasticsearchConnection(getHost(), getPort());
    }

}
