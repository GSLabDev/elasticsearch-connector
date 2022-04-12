/**
 * Copyright (c) 2003-2020, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.2, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.api;

import org.elasticsearch.action.support.WriteRequest.RefreshPolicy;

public enum ElasticsearchRefreshPolicy {
    IMMEDIATE,
    NONE,
    WAIT_UNTIL;

    public RefreshPolicy getRefreshPolicy() {
    	RefreshPolicy refreshpolicy = null;
    	if(this == IMMEDIATE) {
    		refreshpolicy = RefreshPolicy.IMMEDIATE;
    	}else if (this == NONE) {
    		refreshpolicy =  RefreshPolicy.NONE;
		}else if(this == WAIT_UNTIL){
			refreshpolicy = RefreshPolicy.WAIT_UNTIL;
		}
    	return refreshpolicy;
    }
}
