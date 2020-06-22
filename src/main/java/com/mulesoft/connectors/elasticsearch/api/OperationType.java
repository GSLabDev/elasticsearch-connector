package com.mulesoft.connectors.elasticsearch.api;

import org.elasticsearch.action.DocWriteRequest.OpType;

/**
 * Requested operation type to perform on the document
 */
public enum OperationType {
    CREATE,
    DELETE,
    INDEX,
    UPDATE;

    public OpType getOpType() {
        switch (this) {
            case CREATE:
                return OpType.CREATE;
            case DELETE:
                return OpType.DELETE;
            case INDEX:
                return OpType.INDEX;
            case UPDATE:
                return OpType.UPDATE;
            default:
                return null;
        }
    }
}
