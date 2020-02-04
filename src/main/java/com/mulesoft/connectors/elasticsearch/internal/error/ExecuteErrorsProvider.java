/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.internal.error;

import java.util.HashSet;
import java.util.Set;

import org.mule.runtime.extension.api.annotation.error.ErrorTypeProvider;
import org.mule.runtime.extension.api.error.ErrorTypeDefinition;

public class ExecuteErrorsProvider implements ErrorTypeProvider {

    /**
     * Custom Error providers
     */
    @Override
    public Set<ErrorTypeDefinition> getErrorTypes() {
        HashSet<ErrorTypeDefinition> errors = new HashSet<>();
        errors.add(ElasticsearchError.OPERATION_FAILED);
        errors.add(ElasticsearchError.INVALID_AUTH);
        errors.add(ElasticsearchError.INVALID_CONNECTION);
        return errors;
    }
}