/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.api;

import java.util.Map;

import org.mule.runtime.extension.api.annotation.param.ExclusiveOptionals;
import org.mule.runtime.extension.api.annotation.param.NullSafe;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Path;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 *
 */
@ExclusiveOptionals(isOneRequired = true)
public class IndexDocumentOptions {

    /**
     * Provide the JSON file path
     */
    @Parameter
    @Optional
    @Path(acceptedFileExtensions = "json")
    @DisplayName("JSON Input file path")
    private String jsonInputPath;

    /**
     * Provide the Document source
     */
    @Parameter
    @Optional
    @DisplayName("Document Source")
    @NullSafe
    private Map<String, Object> documentSource;

    public IndexDocumentOptions() {
        // This default constructor makes the class DataWeave compatible.
    }
    
    public String getJsonInputPath() {
        return jsonInputPath;
    }

    public Map<String, Object> getDocumentSource() {
        return documentSource;
    }

}
