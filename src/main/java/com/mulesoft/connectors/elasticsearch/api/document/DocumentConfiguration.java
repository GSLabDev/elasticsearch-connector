/**
 * Copyright (c) 2003-2020, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.2, a copy of which has been included with this distribution in the LICENSE.md file.
 */
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
