/**
 * Copyright (c) 2003-2020, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.2, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.internal.error.exception;

import org.mule.runtime.extension.api.exception.ModuleException;

import com.mulesoft.connectors.elasticsearch.internal.error.ElasticsearchErrorTypes;

public class ElasticsearchException extends ModuleException {

    private static final long serialVersionUID = 7693494311617697239L;

    /**
     * Custom Exception handler
     * 
     * @param error
     *            Customized error
     * @param cause
     *            Original exception trace
     */
    public ElasticsearchException(ElasticsearchErrorTypes error, Exception cause) {
        super(error, cause);
    }

}
