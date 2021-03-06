/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.extension.elastic.internal.error;

import static org.mule.runtime.extension.api.error.MuleErrors.CONNECTIVITY;

import java.util.Optional;

import org.mule.runtime.extension.api.error.ErrorTypeDefinition;

/**
 * Custom Errors
 */
public enum ElasticsearchError implements ErrorTypeDefinition<ElasticsearchError> {
    OPERATION_FAILED,
    INVALID_CONNECTION(CONNECTIVITY),
    INVALID_AUTH(CONNECTIVITY);

    private ErrorTypeDefinition<? extends Enum<?>> parent;

    ElasticsearchError(ErrorTypeDefinition<? extends Enum<?>> parent) {
        this.parent = parent;
    }

    ElasticsearchError() {
    }

    @Override
    public Optional<ErrorTypeDefinition<? extends Enum<?>>> getParent() {
        return Optional.ofNullable(parent);
    }
}