/**
 * Copyright (c) 2003-2020, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.2, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.api.response;

import java.io.Serializable;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicRequestLine;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;

import com.mulesoft.connectors.elasticsearch.internal.error.ElasticsearchErrorTypes;
import com.mulesoft.connectors.elasticsearch.internal.error.exception.ElasticsearchException;

/**
 * Holds an elasticsearch response.
 */
public class ElasticsearchResponse implements Serializable{

    private static final long serialVersionUID = 8287834136687129724L;

    /**
     * The request line that generated this response
     */
    private BasicRequestLine requestLine;
    
    /**
     * The response body available (as String), null otherwise
     */
    private String entity;
    
    /**
     * The node that returned this response
     */
    private HttpHost host;
    
    /**
     * The status line of the current response
     */
    private BasicStatusLine statusLine;
    
    /**
     * All the response headers
     */
    private Header[] headers;
    
    /**
     * A list of all warning headers returned in the response.
     */
    private List<String> warnings;
    
    public ElasticsearchResponse() {
        // This default constructor makes the class DataWeave compatible.
    }

    public ElasticsearchResponse(Response response) {
        requestLine = (BasicRequestLine) response.getRequestLine();
        try {
            entity = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            throw new ElasticsearchException(ElasticsearchErrorTypes.OPERATION_FAILED, e);
        }
        host = response.getHost();
        statusLine = (BasicStatusLine) response.getStatusLine();
        headers = response.getHeaders();
        warnings = response.getWarnings();
    }

    public BasicRequestLine getRequestLine() {
        return requestLine;
    }
    
    public String getEntity() {
        return entity;
    }

    public HttpHost getHost() {
        return host;
    }

    public BasicStatusLine getStatusLine() {
        return statusLine;
    }
    
    public Header[] getHeaders() {
        return headers;
    }
    
    public List<String> getWarnings() {
        return warnings;
    }
    
    @Override
    public String toString() {
        return "Response{" +
                "requestLine=" + requestLine +
                ", host=" + host +
                ", response=" + statusLine +
                '}';
    }
}
