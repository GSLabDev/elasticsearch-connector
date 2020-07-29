package com.mulesoft.connectors.elasticsearch.api.document;

import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Summary;

import com.mulesoft.connectors.elasticsearch.api.ElasticsearchRefreshPolicy;

public class UpdateDocumentConfiguration {

    /**
     * Routing is used to determine in which shard the document will reside in.
     */
    @Parameter
    @Optional
    @DisplayName("Routing")
    private String routing;

    /**
     * Time in seconds to wait for primary shard to become available.
     */
    @Parameter
    @Optional(defaultValue = "0")
    @DisplayName("Timeout (Seconds)")
    @Summary("Timeout in seconds to wait for primary shard.")
    private long timeoutInSec;

    /**
     * Refresh policy is used to control when changes made by the requests are made visible to search. Option for refresh policy A) true : Refresh the relevant primary and replica
     * shards (not the whole index) immediately after the operation occurs, so that the updated document appears in search results immediately. B) wait_for : Wait for the changes
     * made by the request to be made visible by a refresh before replying. This doesn�t force an immediate refresh, rather, it waits for a refresh to happen. C) false (default)
     * : Take no refresh related actions. The changes made by this request will be made visible at some point after the request returns.
     */
    @Parameter
    @Optional
    @DisplayName("Refresh policy")
    private ElasticsearchRefreshPolicy refreshPolicy;

    /**
     * How many times to retry the update operation if the document to update has been changed by another operation between the get and indexing phases of the update operation
     */
    @Parameter
    @Optional(defaultValue = "0")
    @DisplayName("Retry on Conflict")
    @Summary("How many times to retry the update operation if the document to update has been changed by another operation between the get and indexing phases of the update operation")
    private int retryOnConflict;

    /**
     * Enable or disable source retrieval
     */
    @Parameter
    @Optional(defaultValue = "false")
    @DisplayName("Fetch Source")
    private boolean fetchSource;

    /**
     * If set, only perform this update request if the document was last modification was assigned this sequence number.
     */
    @Parameter
    @Optional
    @DisplayName("If Seq No")
    private long ifSeqNo;

    /**
     * If set, only perform this update request if the document was last modification was assigned this primary term.
     */
    @Parameter
    @Optional
    @DisplayName("If Primary Term")
    private long ifPrimaryTerm;

    /**
     * Enable or disable the noop detection
     */
    @Parameter
    @Optional(defaultValue = "true")
    @DisplayName("Noop Detection")
    private boolean detectNoop;

    /**
     * Indicate that the script must run regardless of whether the document exists or not
     */
    @Parameter
    @Optional(defaultValue = "false")
    @DisplayName("Scripted Upsert")
    private boolean scriptedUpsert;

    /**
     * Indicate that the partial document must be used as the upsert document if it does not exist yet.
     */
    @Parameter
    @Optional(defaultValue = "false")
    @DisplayName("Doc Upsert")
    private boolean docAsUpsert;

    /**
     * The number of shard copies that must be active before proceeding with the update operation.
     */
    @Parameter
    @Optional
    @DisplayName("Wait for Active Shards")
    private int waitForActiveShards;

    public UpdateDocumentConfiguration() {
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

    public int getRetryOnConflict() {
        return retryOnConflict;
    }

    public boolean isFetchSource() {
        return fetchSource;
    }

    public long getIfSeqNo() {
        return ifSeqNo;
    }

    public long getIfPrimaryTerm() {
        return ifPrimaryTerm;
    }

    public boolean isDetectNoop() {
        return detectNoop;
    }

    public boolean isScriptedUpsert() {
        return scriptedUpsert;
    }

    public boolean isDocAsUpsert() {
        return docAsUpsert;
    }

    public int getWaitForActiveShards() {
        return waitForActiveShards;
    }

}
