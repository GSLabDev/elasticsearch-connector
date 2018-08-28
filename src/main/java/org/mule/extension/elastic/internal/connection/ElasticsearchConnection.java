/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.extension.elastic.internal.connection;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import javax.net.ssl.SSLContext;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.mule.extension.elastic.internal.error.ElasticsearchError;
import org.mule.extension.elastic.internal.error.exception.ElasticsearchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 * 
 *         Provides HTTP and HTTPS connection with Elasticsearch
 */
public final class ElasticsearchConnection {

    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchConnection.class);
    private RestHighLevelClient client;

    public ElasticsearchConnection(String host, int port) {
        logger.info("Using host:" + host + " and port:" + port);
        this.client = new RestHighLevelClient(RestClient.builder(new HttpHost(host, port, "http")));
    }

    public ElasticsearchConnection(String host, int port, String username, String password) {
        logger.info("Using host:" + host + " port:" + port + " and user:" + username);
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
        RestClientBuilder builder = RestClient.builder(new HttpHost(host, port)).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {

            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }
        });
        this.client = new RestHighLevelClient(builder);
    }

    public ElasticsearchConnection(String host, int port, String userName, String password, String trustStoreType, String trustStorePath, String trustStorePassword) {
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
                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider).setSSLContext(sslContext);
                    } else {
                        return httpClientBuilder.setSSLContext(sslContext);
                    }
                }
            });

            this.client = new RestHighLevelClient(builder);
        } catch (Exception e) {
            throw new ElasticsearchException(ElasticsearchError.INVALID_AUTH, e);
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
