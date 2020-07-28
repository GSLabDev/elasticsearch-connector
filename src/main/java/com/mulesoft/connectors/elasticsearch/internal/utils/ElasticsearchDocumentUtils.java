/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
/**
 *
 */
package com.mulesoft.connectors.elasticsearch.internal.utils;

import static com.mulesoft.connectors.elasticsearch.internal.utils.ElasticsearchUtils.ifPresent;

import java.io.IOException;
import org.apache.log4j.Logger;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.common.unit.TimeValue;

import com.mulesoft.connectors.elasticsearch.api.document.DeleteDocumentConfiguration;

public class ElasticsearchDocumentUtils {
    
    /**
     * Logging object
     */
    private static final Logger logger = Logger.getLogger(ElasticsearchDocumentUtils.class.getName());

    private ElasticsearchDocumentUtils() {
    }

    public static void configureDeleteDocumentReq(DeleteRequest deleteReq, DeleteDocumentConfiguration deleteDocumentConfiguration) throws IOException {
        
        if (deleteDocumentConfiguration.getTimeoutInSec() != 0) {
            deleteReq.timeout(TimeValue.timeValueSeconds(deleteDocumentConfiguration.getTimeoutInSec()));
        }
        if (deleteDocumentConfiguration.getVersion() != 0) {
            deleteReq.version(deleteDocumentConfiguration.getVersion());
        }

        ifPresent(deleteDocumentConfiguration.getRouting(), routingValue -> deleteReq.routing(routingValue));
        ifPresent(deleteDocumentConfiguration.getRefreshPolicy(), refreshPolicyValue -> deleteReq.setRefreshPolicy(refreshPolicyValue.getRefreshPolicy()));
        ifPresent(deleteDocumentConfiguration.getVersionType(), versionTypeValue -> deleteReq.versionType(versionTypeValue.getVersionType()));
        
        logger.debug("Delete request : " + deleteReq);
    }
}
