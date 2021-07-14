/**
 * Copyright © 2021 Axiomatics. The software in this package is published under the terms of the "AXIOMATICS END USER LICENSE AGREEMENT", a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.api;

import java.io.Serializable;

import org.mule.runtime.api.util.MultiMap;

public class ResponseAttributes implements Serializable {

    private static final long serialVersionUID = -1391521228623968966L;
    private int statusCode;
    private MultiMap<String, String> headers;

    public ResponseAttributes() {
        // Empty constructor required for DataWeave
    }

    public ResponseAttributes(int statusCode, MultiMap<String, String> headers) {
        this.statusCode = statusCode;
        this.headers = headers;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public MultiMap<String, String> getHeaders() {
        return headers;
    }
}
