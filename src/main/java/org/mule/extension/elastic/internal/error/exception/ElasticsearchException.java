/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.extension.elastic.internal.error.exception;

import org.mule.extension.elastic.internal.error.ElasticsearchError;
import org.mule.runtime.extension.api.exception.ModuleException;

public class ElasticsearchException extends ModuleException {

    /**
     * Custom Exception handler
     * 
     * @param error
     *            Customized error
     * @param cause
     *            Original exception trace
     */
    public ElasticsearchException(ElasticsearchError error, Exception cause) {
        super(error, cause);
    }

}
