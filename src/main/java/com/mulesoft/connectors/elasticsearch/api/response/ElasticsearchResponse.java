package com.mulesoft.connectors.elasticsearch.api.response;

import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.RequestLine;
import org.apache.http.StatusLine;
import org.elasticsearch.client.Response;

public class ElasticsearchResponse {

    private RequestLine requestLine;
    private HttpHost host;
    private StatusLine statusLine;
    private List<String> warnings;
    
    public ElasticsearchResponse() {
    }

    public ElasticsearchResponse(Response response) {
        requestLine = response.getRequestLine();
        host = response.getHost();
        statusLine = response.getStatusLine();
        warnings = response.getWarnings();
    }

    /**
     * Returns the request line that generated this response
     */
    public RequestLine getRequestLine() {
        return requestLine;
    }

    /**
     * Returns the node that returned this response
     */
    public HttpHost getHost() {
        return host;
    }

    /**
     * Returns the status line of the current response
     */
    public StatusLine getStatusLine() {
        return statusLine;
    }
    
    /**
     * Returns a list of all warning headers returned in the response.
     */
    public List<String> getWarnings() {
        return warnings;
    }

}
