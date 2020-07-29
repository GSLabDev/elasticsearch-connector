package com.mulesoft.connectors.elasticsearch.api.document;

import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Summary;

import com.mulesoft.connectors.elasticsearch.api.ElasticsearchRefreshPolicy;

public class BaseDocumentConfiguration {

    /**
     * Routing is used to determine in which shard the document will reside in
     */
    @Parameter
    @Optional
    @DisplayName("Routing")
    private String routing;

    /**
     * Time in seconds to wait for primary shard to become available
     */
    @Parameter
    @Optional(defaultValue = "0")
    @DisplayName("Timeout (Seconds)")
    @Summary("Timeout in seconds to wait for primary shard")
    private long timeoutInSec;

    /**
     * Refresh policy is used to control when changes made by the requests are made visible to search. Option for refresh policy A) true : Refresh the relevant primary
     * and replica shards (not the whole index) immediately after the operation occurs, so that the updated document appears in search results immediately. B) wait_for :
     * Wait for the changes made by the request to be made visible by a refresh before replying. This doesn�t force an immediate refresh, rather, it waits for a
     * refresh to happen. C) false (default) : Take no refresh related actions. The changes made by this request will be made visible at some point after the request
     * returns.
     */
    @Parameter
    @Optional
    @DisplayName("Refresh policy")
    private ElasticsearchRefreshPolicy refreshPolicy;

    public BaseDocumentConfiguration() {
        // This default constructor makes the class DataWeave compatible.
    }

    public String getRouting() {
        return routing;
    }

    public long getTimeoutInSec() {
        return timeoutInSec;
    }

    public ElasticsearchRefreshPolicy getRefreshPolicy() {
        return refreshPolicy;
    }
}
