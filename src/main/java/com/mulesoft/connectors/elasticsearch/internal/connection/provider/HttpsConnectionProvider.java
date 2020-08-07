/**
 * Copyright (c) 2003-2020, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.2, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.internal.connection.provider;

import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.dsl.xml.ParameterDsl;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;

import com.mulesoft.connectors.elasticsearch.internal.connection.ElasticsearchConnection;
import com.mulesoft.connectors.elasticsearch.internal.connection.provider.configuration.TrustStoreConfiguration;
import com.mulesoft.connectors.elasticsearch.internal.connection.provider.configuration.UserConfiguration;
import com.mulesoft.connectors.elasticsearch.internal.error.ElasticsearchErrorTypes;
import com.mulesoft.connectors.elasticsearch.internal.error.exception.ElasticsearchException;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 * 
 *         Provides HTTPS ElasticSearch connection
 */
@Alias("https-connection")
@DisplayName("HTTPS Connection")
public class HttpsConnectionProvider extends ElasticsearchBaseConnectionProvider {

    @ParameterGroup(name = "User Credentials")
    @ParameterDsl(allowInlineDefinition = true)
    private UserConfiguration userConfiguration;

    @ParameterGroup(name = "Trust Store")
    @ParameterDsl(allowInlineDefinition = true)
    private TrustStoreConfiguration trustStoreconfiguration;

    @Override
    public ElasticsearchConnection connect() {
        try {
            return new ElasticsearchConnection(getHost(), getPort(), userConfiguration.getUserName(), userConfiguration.getPassword(), trustStoreconfiguration.getTrustStoreType(),
                    trustStoreconfiguration.getTrustStorePath(), trustStoreconfiguration.getTrustStorePassword());
        } catch (Exception e) {
            throw new ElasticsearchException(ElasticsearchErrorTypes.CONNECTIVITY, e);
        }
    }
}
