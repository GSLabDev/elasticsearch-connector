package com.mulesoft.connectors.elasticsearch.api.document;

import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Summary;

import com.mulesoft.connectors.elasticsearch.api.DocumentFetchSourceOptions;
import com.mulesoft.connectors.elasticsearch.api.ElasticsearchVersionType;

public class GetDocumentConfiguration {

    /**
     * Enable or disable source retrieval
     */
    @Parameter
    @Optional
    @DisplayName("Source retrieval")
    private DocumentFetchSourceOptions fetchSourceContext;

    /**
     * Routing is used to determine in which shard the document will reside in
     */
    @Parameter
    @Optional
    @DisplayName("Routing")
    private String routing;

    /**
     * Preference value
     */
    @Parameter
    @Optional
    @DisplayName("Preference value")
    private String preference;

    /**
     * Set realtime flag
     */
    @Parameter
    @Optional(defaultValue = "true")
    @DisplayName("Set realtime flag")
    private boolean realtime;

    /**
     * Perform a refresh before retrieving the document
     */
    @Parameter
    @Optional(defaultValue = "false")
    @DisplayName("Refresh")
    @Summary("Perform a refresh before retrieving the document")
    private boolean refresh;

    /**
     * Version number of the indexed document
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

    public GetDocumentConfiguration() {
        // This default constructor makes the class DataWeave compatible.
    }

    public DocumentFetchSourceOptions getFetchSourceContext() {
        return fetchSourceContext;
    }

    public String getRouting() {
        return routing;
    }

    public String getPreference() {
        return preference;
    }

    public boolean isRealtime() {
        return realtime;
    }

    public boolean isRefresh() {
        return refresh;
    }

    public long getVersion() {
        return version;
    }

    public ElasticsearchVersionType getVersionType() {
        return versionType;
    }

}
