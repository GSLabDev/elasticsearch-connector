/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.extension.elastic.internal.connection;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import org.mule.extension.elastic.api.TrustStoreConfiguration;
import org.mule.extension.elastic.api.UserConfiguration;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.dsl.xml.ParameterDsl;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;

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
    public ElasticsearchConnection connect() throws ConnectionException {
        try {
            return new ElasticsearchConnection(getHost(), getPort(), userConfiguration.getUserName(), userConfiguration.getPassword(), trustStoreconfiguration.getTrustStoreType(),
                    trustStoreconfiguration.getTrustStorePath(), trustStoreconfiguration.getTrustStorePassword());
        } catch (KeyManagementException | KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            throw new ConnectionException("Failed to establish secure connection with host", e);
        }
    }
}
