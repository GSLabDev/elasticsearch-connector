package com.mulesoft.connectors.elasticsearch.api;

import org.elasticsearch.action.support.WriteRequest.RefreshPolicy;

public enum ElasticsearchRefreshPolicy {
    IMMEDIATE,
    NONE,
    WAIT_UNTIL;

    public RefreshPolicy getRefreshPolicy() {
        switch (this) {
            case IMMEDIATE:
                return RefreshPolicy.IMMEDIATE;
            case NONE:
                return RefreshPolicy.NONE;
            case WAIT_UNTIL:
                return RefreshPolicy.WAIT_UNTIL;
            default:
                return null;
        }
    }
}
