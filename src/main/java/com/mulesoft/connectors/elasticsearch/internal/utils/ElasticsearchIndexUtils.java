/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
/**
 *
 */
package com.mulesoft.connectors.elasticsearch.internal.utils;

import static com.mulesoft.connectors.elasticsearch.internal.utils.ElasticsearchUtils.ifPresent;

import java.io.IOException;
import java.util.Map;
import org.apache.log4j.Logger;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.Settings.Builder;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;

import com.mulesoft.connectors.elasticsearch.api.IndexConfiguration;

public class ElasticsearchIndexUtils {
    
    /**
     * Logging object
     */
    private static final Logger logger = Logger.getLogger(ElasticsearchIndexUtils.class.getName());

    private ElasticsearchIndexUtils() {
    }

    public static CreateIndexRequest getCreateIndexReq(String index, IndexConfiguration indexConfiguration) throws IOException {
        CreateIndexRequest createIndexReq = new CreateIndexRequest(index);

        if(indexConfiguration == null) {
            return createIndexReq;
        }
        
        if (indexConfiguration.getIndexSettings() != null) {
            final Builder settingsBuilder = Settings.builder();
            for (Map.Entry<String, String> entry : indexConfiguration.getIndexSettings().entrySet()) {
                settingsBuilder.put(entry.getKey(), entry.getValue());
            }
            createIndexReq.settings(settingsBuilder);
        } else if (indexConfiguration.getIndexSettingsJSONFile() != null) {
            createIndexReq.settings(ElasticsearchUtils.readFileToString(indexConfiguration.getIndexSettingsJSONFile()), XContentType.JSON);
        }

        if (indexConfiguration.getIndexMappingsJSONFile() != null) {
            createIndexReq.mapping(ElasticsearchUtils.readFileToString(indexConfiguration.getIndexMappingsJSONFile()), XContentType.JSON);
        }

        if (indexConfiguration.getSourceJSONFile() != null) {
            createIndexReq.source(ElasticsearchUtils.readFileToString(indexConfiguration.getSourceJSONFile()), XContentType.JSON);
        }

        ifPresent(indexConfiguration.getIndexAlias(), indexAliasValue -> createIndexReq.alias(new Alias(indexAliasValue)));
        if (indexConfiguration.getTimeoutInSec() != 0) {
            createIndexReq.setTimeout(TimeValue.timeValueSeconds(indexConfiguration.getTimeoutInSec()));
        }
        if (indexConfiguration.getMasterNodeTimeoutInSec() != 0) {
            createIndexReq.setMasterTimeout(TimeValue.timeValueSeconds(indexConfiguration.getMasterNodeTimeoutInSec()));
        }
        if (indexConfiguration.getWaitForActiveShards() != 0) {
            createIndexReq.waitForActiveShards(ActiveShardCount.from(indexConfiguration.getWaitForActiveShards()));
        }
        
        logger.debug("Create index request : " + createIndexReq);
        return createIndexReq;
    }
}
