/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.api;

import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.Summary;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 *
 */
public class IndexOptions {

    /**
     * Whether specified concrete indices should be ignored when unavailable (missing or closed).
     */
    @Parameter
    @Summary("Whether specified concrete indices should be ignored when unavailable (missing or closed)")
    private boolean ignoreUnavailable;

    /**
     * Whether to ignore if a wildcard expression resolves to no concrete indices.
     */
    @Parameter
    @Summary(" Whether to ignore if a wildcard expression resolves to no concrete indices.")
    private boolean allowNoIndices;

    /**
     * Whether wildcard expressions should get expanded to open indices.
     */
    @Parameter
    @Summary("Whether wildcard expressions should get expanded to open indices.")
    private boolean expandWildcardsOpen;

    /**
     * Whether wildcard expressions should get expanded to closed indices.
     */
    @Parameter
    @Summary("Whether wildcard expressions should get expanded to closed indices.")
    private boolean expandWildcardsClosed;

    /**
     * Whether aliases pointing to multiple indices are allowed.
     */
    @Parameter
    @Summary("Whether aliases pointing to multiple indices are allowed.")
    private boolean allowAliasesToMultipleIndices;

    /**
     * Whether execution on closed indices is allowed.
     */
    @Parameter
    @Summary("Whether execution on closed indices is allowed..")
    private boolean forbidClosedIndices;

    /**
     * Whether aliases should be ignored (when resolving a wildcard).
     */
    @Parameter
    @Summary("Whether aliases should be ignored (when resolving a wildcard).")
    private boolean ignoreAliases;

    /**
     * Whether indices that are marked as throttled should be ignored.
     */
    @Parameter
    @Summary("Whether indices that are marked as throttled should be ignored.")
    private boolean ignoreThrottled;

    public boolean isIgnoreUnavailable() {
        return ignoreUnavailable;
    }

    public boolean isAllowNoIndices() {
        return allowNoIndices;
    }

    public boolean isExpandWildcardsOpen() {
        return expandWildcardsOpen;
    }

    public boolean isExpandWildcardsClosed() {
        return expandWildcardsClosed;
    }

    public boolean isAllowAliasesToMultipleIndices() {
        return allowAliasesToMultipleIndices;
    }

    public boolean isForbidClosedIndices() {
        return forbidClosedIndices;
    }

    public boolean isIgnoreAliases() {
        return ignoreAliases;
    }

    public boolean isIgnoreThrottled() {
        return ignoreThrottled;
    }
}
