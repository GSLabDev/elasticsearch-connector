/**
 * Copyright (c) 2003-2020, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.2, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.api;

import java.util.List;

import org.mule.runtime.extension.api.annotation.param.NullSafe;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;

public class DocumentFetchSourceOptions {

    /**
     * Fetch the source of the search document
     */
    @Parameter
    @Optional(defaultValue = "true")
    private boolean fetchSource;

    /**
     * List of the field that are included into the document source
     */
    @Parameter
    @Optional
    @NullSafe
    private List<String> includeFields;

    /**
     * List of the field that are excluded from the document source
     */
    @Parameter
    @Optional
    @NullSafe
    private List<String> excludeFields;
    
    public DocumentFetchSourceOptions() {
        // This default constructor makes the class DataWeave compatible.
    }

    public boolean isFetchSource() {
        return fetchSource;
    }

    public List<String> getIncludeFields() {
        return includeFields;
    }

    public List<String> getExcludeFields() {
        return excludeFields;
    }

}
