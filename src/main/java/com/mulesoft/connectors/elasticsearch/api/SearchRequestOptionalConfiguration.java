/**
 * Copyright (c) 2003-2020, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.2, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.api;

import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;

public class SearchRequestOptionalConfiguration {

    /**
     * Sets if this request should allow partial results.
     */
    @Parameter
    @Optional
    private boolean allowPartialSearchResults;

    /**
     * Sets if this request should use the request cache or not.
     */
    @Parameter
    @Optional
    private boolean requestCache;

    /**
     * The number of shard results that should be reduced at once on the coordinating node.
     */
    @Parameter
    @Optional(defaultValue="2")
    private int batchedReduceSize;

    /**
     * Whether network round-trips should be minimized when executing cross-cluster search requests.
     */
    @Parameter
    @Optional
    private boolean ccsMinimizeRoundtrips;

    /**
     * The number of shard requests that should be executed concurrently on a single node.
     */
    @Parameter
    @Optional(defaultValue="1")
    private int maxConcurrentShardRequests;

    /**
     * A threshold that enforces a pre-filter roundtrip to pre-filter search shards based on query rewriting if the number of shards the search request expands to exceeds the
     * threshold.
     */
    @Parameter
    @Optional(defaultValue="1")
    private int preFilterShardSize;
    
    public SearchRequestOptionalConfiguration() {
        // This default constructor makes the class DataWeave compatible.
    }

    public boolean isAllowPartialSearchResults() {
        return allowPartialSearchResults;
    }

    public boolean isRequestCache() {
        return requestCache;
    }

    public int getBatchedReduceSize() {
        return batchedReduceSize;
    }

    public boolean isCcsMinimizeRoundtrips() {
        return ccsMinimizeRoundtrips;
    }

    public int getMaxConcurrentShardRequests() {
        return maxConcurrentShardRequests;
    }

    public int getPreFilterShardSize() {
        return preFilterShardSize;
    }
}
