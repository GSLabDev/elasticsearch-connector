/**
 * Copyright (c) 2003-2020, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.2, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.api;

import org.elasticsearch.index.VersionType;

public enum ElasticsearchVersionType {
    EXTERNAL,
    EXTERNAL_GTE,
    INTERNAL;

    public VersionType getVersionType() {
        switch (this) {
            case EXTERNAL:
                return VersionType.EXTERNAL;
            case EXTERNAL_GTE:
                return VersionType.EXTERNAL_GTE;
            case INTERNAL:
                return VersionType.INTERNAL;
            default:
                return null;
        }
    }
}
