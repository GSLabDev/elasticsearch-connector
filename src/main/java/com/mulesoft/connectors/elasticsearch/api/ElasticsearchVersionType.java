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
    	
    	VersionType versiontype = null;
        
    	if(this == EXTERNAL) {
    		versiontype = VersionType.EXTERNAL;
    	}else if (this == EXTERNAL_GTE) {
    		versiontype = VersionType.EXTERNAL_GTE;
		}else if (this == INTERNAL) {
			versiontype = VersionType.INTERNAL;
		}
    	return versiontype;
    }
}
