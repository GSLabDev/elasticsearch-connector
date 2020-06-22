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
