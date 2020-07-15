/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.internal.error;

import java.util.Optional;

import org.mule.runtime.extension.api.error.ErrorTypeDefinition;

/**
 * Custom Errors
 */
public enum ElasticsearchErrorTypes implements ErrorTypeDefinition<ElasticsearchErrorTypes> {
    
    EXECUTION,
    OPERATION_FAILED(EXECUTION),
    CONNECTIVITY;
    
    private ErrorTypeDefinition<? extends Enum<?>> parent;

    ElasticsearchErrorTypes(ErrorTypeDefinition<? extends Enum<?>> parent) {
        this.parent = parent;
    }

    ElasticsearchErrorTypes() {
    }

    @Override
    public Optional<ErrorTypeDefinition<? extends Enum<?>>> getParent() {
        return Optional.ofNullable(parent);
    }
}