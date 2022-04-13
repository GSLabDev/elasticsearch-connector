/**
 * Copyright (c) 2003-2020, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.2, a copy of which has been included with this distribution in the LICENSE.md file.
 */
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
    	
    	OpType getoptype = null;
    	
    	if (this == CREATE) {
    		getoptype = OpType.CREATE;
		} else if (this == DELETE) {
			getoptype = OpType.DELETE;
		}else if (this == INDEX) {
			getoptype = OpType.INDEX;
		}else if (this == UPDATE) {
			getoptype = OpType.UPDATE;
		}
    	
    	return getoptype;
    }
}
