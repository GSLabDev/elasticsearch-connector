/**
 * Copyright (c) 2003-2020, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.2, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.internal.connection;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mulesoft.connectors.elasticsearch.api.ProxyConfiguration;
import com.mulesoft.connectors.elasticsearch.internal.connection.provider.configuration.UserConfiguration;
import com.mulesoft.connectors.elasticsearch.internal.error.ElasticsearchErrorTypes;
import com.mulesoft.connectors.elasticsearch.internal.error.exception.ElasticsearchException;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 * 
 *         Provides HTTP and HTTPS connection with Elasticsearch
 */
public final class ElasticsearchConnection {

    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchConnection.class);
    private RestHighLevelClient client;
    private ProxyConfiguration proxyConfiguration;
    private UserConfiguration configuration;

    public ElasticsearchConnection(String host, int port) {
        logger.info("Using host:" + host + " and port:" + port);
        this.client = new RestHighLevelClient(RestClient.builder(new HttpHost(host, port, "http")));
    }

    public ElasticsearchConnection(String host, int port, UserConfiguration userConfiguration, ProxyConfiguration proxyConfig) {
    	this.configuration = userConfiguration;
    	String username = configuration.getUserName();
    	String password = configuration.getPassword();
        logger.info("Using host:" + host + " port:" + port + " and user:" + username);
        this.proxyConfiguration = proxyConfig;
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
        RestClientBuilder builder = RestClient.builder(new HttpHost(host, port)).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {

			@Override
			public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
				httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
				if (proxyConfiguration != null) {
					httpClientBuilder.setProxy(new HttpHost(proxyConfiguration.getHost(), proxyConfiguration.getPort(),
							proxyConfiguration.getProtocol()));
				}
				return httpClientBuilder;
			}
		}).setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
			@Override
			public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
				return requestConfigBuilder.setConnectTimeout((int) TimeUnit.MILLISECONDS.convert(configuration.getConnectTimeout(), configuration.getTimeUnit()))
						.setSocketTimeout((int) TimeUnit.MILLISECONDS.convert(configuration.getSocketTimeout(), configuration.getTimeUnit()));
			}
		});
        this.client = new RestHighLevelClient(builder);
    }

    public ElasticsearchConnection(String host, int port, UserConfiguration userConfiguration, String trustStoreType, String trustStorePath, String trustStorePassword, ProxyConfiguration proxyConfig) {
    	this.configuration = userConfiguration;
    	String userName = configuration.getUserName();
    	String password = configuration.getPassword();
    	logger.info("Using host:" + host + " port:" + port + " user:" + userName + " truststore:" + trustStorePath + " and truststore password:" + trustStorePassword);
    	this.proxyConfiguration = proxyConfig;
    	KeyStore truststore;
        try {
            truststore = KeyStore.getInstance(trustStoreType);

            Path keyStorePath = Paths.get(trustStorePath);
            try (InputStream is = Files.newInputStream(keyStorePath)) {
                truststore.load(is, trustStorePassword.toCharArray());
            }
            SSLContextBuilder sslBuilder = SSLContexts.custom().loadTrustMaterial(truststore, null);
            SSLContext sslContext = sslBuilder.build();
            RestClientBuilder builder = RestClient.builder(new HttpHost(host, port, "https")).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {

                @Override
                public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                    if (userName != null && password != null) {
                        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, password));
                        httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider).setSSLContext(sslContext);
                        if(proxyConfiguration != null) {
                        	httpClientBuilder.setProxy(new HttpHost(proxyConfiguration.getHost(), proxyConfiguration.getPort(), proxyConfiguration.getProtocol()));
                        }
                        return httpClientBuilder;
                    } else {
                        httpClientBuilder.setSSLContext(sslContext);
                        if(proxyConfiguration != null) {
                        	httpClientBuilder.setProxy(new HttpHost(proxyConfiguration.getHost(), proxyConfiguration.getPort(), proxyConfiguration.getProtocol()));
                        }
                        return httpClientBuilder;
                    }
                }
            }).setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
    			@Override
    			public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
    				return requestConfigBuilder.setConnectTimeout((int) TimeUnit.MILLISECONDS.convert(configuration.getConnectTimeout(), configuration.getTimeUnit()))
    						.setSocketTimeout((int) TimeUnit.MILLISECONDS.convert(configuration.getSocketTimeout(), configuration.getTimeUnit()));
    			}
    		});

            this.client = new RestHighLevelClient(builder);
        } catch (Exception e) {
            throw new ElasticsearchException(ElasticsearchErrorTypes.CONNECTIVITY, e);
        }
    }

    public RestHighLevelClient getElasticsearchConnection() {
        return this.client;
    }

    public void invalidate() throws IOException {
        this.client.close();
        logger.info("Connection invalidated......!");
    }
}
