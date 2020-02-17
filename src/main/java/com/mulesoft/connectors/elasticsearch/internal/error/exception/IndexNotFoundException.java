/**
 * Copyright © 2019 Braintree, a service of PayPal Inc. The software in this package is published under the terms of the Commercial Free Software license, a copy of which has been included with this distribution in the LICENSE.md file
 */
package com.mulesoft.connectors.elasticsearch.internal.error.exception;

import com.mulesoft.connectors.elasticsearch.internal.error.ElasticsearchErrorTypes;

public class IndexNotFoundException extends ElasticsearchException {

    private static final long serialVersionUID = 2217597013808231713L;

    public IndexNotFoundException(Exception cause) {
        super(ElasticsearchErrorTypes.INDEX_NOT_FOUND, cause);
    }
}
