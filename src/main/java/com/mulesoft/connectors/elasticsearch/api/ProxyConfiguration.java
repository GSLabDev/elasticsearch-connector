/**
 * Copyright © 2021 Axiomatics. The software in this package is published under the terms of the "AXIOMATICS END USER LICENSE AGREEMENT", a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.api;

import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.dsl.xml.TypeDsl;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;

@Alias("proxy")
@TypeDsl(allowTopLevelDefinition = true)
public class ProxyConfiguration {

    /**
     * Host where the proxy requests will be sent.
     */
    @Parameter
    private String host;

    /**
     * Port where the proxy requests will be sent.
     */
    @Parameter
    private int port = Integer.MAX_VALUE;

    /**
     * Protocol - http/https
     */
    @Parameter
    @Optional(defaultValue = "http")
    private String protocol;
    
    public String getHost() {
        return host;
    }


    public int getPort() {
        return port;
    }


    public String getProtocol() {
		return protocol;
	}


	public ProxyConfiguration() {
        // Empty constructor required for DataWeave
    }

    public ProxyConfiguration(String host, int port, String protocol) {
        this.host = host;
        this.port = port;
        this.protocol = protocol;
    }

}
