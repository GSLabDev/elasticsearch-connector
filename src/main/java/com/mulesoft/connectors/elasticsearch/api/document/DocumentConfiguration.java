package com.mulesoft.connectors.elasticsearch.api.document;

import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import com.mulesoft.connectors.elasticsearch.api.ElasticsearchVersionType;

public class DocumentConfiguration extends BaseDocumentConfiguration{

    /**
     * Version number of the indexed document. It will control the version of the document the operation is intended to be executed against.
     */
    @Parameter
    @Optional
    @DisplayName("Version")
    private long version;

    /**
     * Version type: internal, external, external_gte
     */
    @Parameter
    @Optional
    @DisplayName("Version Type")
    private ElasticsearchVersionType versionType;

    public DocumentConfiguration() {
        // This default constructor makes the class DataWeave compatible.
    }

    public long getVersion() {
        return version;
    }

    public ElasticsearchVersionType getVersionType() {
        return versionType;
    }
}
