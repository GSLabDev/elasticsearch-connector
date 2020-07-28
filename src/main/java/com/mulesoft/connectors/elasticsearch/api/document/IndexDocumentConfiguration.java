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
