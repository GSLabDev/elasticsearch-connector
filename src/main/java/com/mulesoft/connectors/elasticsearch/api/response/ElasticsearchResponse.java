package com.mulesoft.connectors.elasticsearch.api.response;

import java.io.Serializable;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.RequestLine;
import org.apache.http.StatusLine;
import org.elasticsearch.client.Response;

/**
 * Holds an elasticsearch response.
 */
public class ElasticsearchResponse implements Serializable{

    private static final long serialVersionUID = 8287834136687129724L;

    /**
     * Returns the request line that generated this response
     */
    private RequestLine requestLine;
    
    /**
     * Returns the node that returned this response
     */
    private HttpHost host;
    
    /**
     * Returns the status line of the current response
     */
    private StatusLine statusLine;
    
    /**
     * Returns all the response headers
     */
    private Header[] headers;
    
    /**
     * Returns a list of all warning headers returned in the response.
     */
    private List<String> warnings;
    
    public ElasticsearchResponse() {
    }

    public ElasticsearchResponse(Response response) {
        requestLine = response.getRequestLine();
        host = response.getHost();
        statusLine = response.getStatusLine();
        headers = response.getHeaders();
        warnings = response.getWarnings();
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public HttpHost getHost() {
        return host;
    }

    public StatusLine getStatusLine() {
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
