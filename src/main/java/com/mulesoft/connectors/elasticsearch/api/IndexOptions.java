/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.api;

import org.mule.runtime.extension.api.annotation.param.Parameter;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 *
 */
public class IndexOptions {

    @Parameter
    private boolean ignoreUnavailable;

    @Parameter
    private boolean allowNoIndices;

    @Parameter
    private boolean expandToOpenIndices;

    @Parameter
    private boolean expandToClosedIndices;

    @Parameter
    private boolean allowAliasesToMultipleIndices;

    @Parameter
    private boolean forbidClosedIndices;

    @Parameter
    private boolean ignoreAliases;
    
    @Parameter
    private boolean ignoreThrottled;

    public boolean isIgnoreUnavailable() {
        return ignoreUnavailable;
    }

    public boolean isAllowNoIndices() {
        return allowNoIndices;
    }

    public boolean isExpandToOpenIndices() {
        return expandToOpenIndices;
    }

    public boolean isExpandToClosedIndices() {
        return expandToClosedIndices;
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
