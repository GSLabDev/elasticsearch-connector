/**
 * Copyright (c) 2003-2020, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.2, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.api.document;

import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Summary;

import com.mulesoft.connectors.elasticsearch.api.OperationType;

public class IndexDocumentConfiguration extends DocumentConfiguration {

    /**
     * Type of the operation. When create type is used, the index operation will fail if a document by that id already exists in the index.
     */
    @Parameter
    @Optional
    @DisplayName("Operation type")
    private OperationType operationType;

    /**
     * Name of the ingest pipeline to be executed before indexing the document
     */
    @Parameter
    @Optional
    @DisplayName("Pipeline")
    @Summary("The name of the ingest pipeline to be executed before indexing the document")
    private String pipeline;

    public IndexDocumentConfiguration() {
        // This default constructor makes the class DataWeave compatible.
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public String getPipeline() {
        return pipeline;
    }
}
