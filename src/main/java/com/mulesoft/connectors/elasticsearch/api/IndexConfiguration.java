/**
 * Copyright (c) 2003-2020, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.2, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.api;

import java.util.Map;

import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Summary;

public class IndexConfiguration {

    /**
     * Settings for the index
     */
    @Parameter
    @Optional
    @DisplayName("Index Settings")
    private Map<String, String> indexSettings;

    /**
     * Path of the JSON file which specifies Index Settings.
     */
    @Parameter
    @Optional
    @DisplayName("Index Settings (JSON file Path)")
    private String indexSettingsJSONFile;

    /**
     * Path of the JSON file which specifies Index Mapping.
     */
    @Parameter
    @Optional
    @DisplayName("Index Mappings (JSON file Path)")
    private String indexMappingsJSONFile;

    /**
     * The alias of the index
     */
    @Parameter
    @Optional
    @DisplayName("Index Alias")
    private String indexAlias;

    /**
     * Path of the JSON file. The whole source including all of its sections (mappings, settings and aliases) can be provided in this json file.
     */
    @Parameter
    @Optional
    @DisplayName("Source (Mappings, Settings and Aliases) (JSON file Path)")
    @Summary("The whole source including all of its sections (mappings, settings and aliases) can be provided in this file.")
    private String sourceJSONFile;

    /**
     * Timeout in seconds to wait for the all the nodes to acknowledge the index creation.
     */
    @Parameter
    @Optional(defaultValue = "0")
    @DisplayName("Timeout (Seconds)")
    @Summary("Timeout in seconds to wait for the all the nodes to acknowledge the index creation.")
    private long timeoutInSec;

    /**
     * Timeout in seconds to connect to the master node.
     */
    @Parameter
    @Optional(defaultValue = "0")
    @DisplayName("Master Node Timeout (Seconds)")
    @Summary("Timeout in seconds to connect to the master node.")
    private long masterNodeTimeoutInSec;

    /**
     * The number of active shard copies to wait for before the create index.
     */
    @Parameter
    @Optional(defaultValue = "0")
    @DisplayName("Wait for Active Shards")
    private int waitForActiveShards;

    public IndexConfiguration() {
        // This default constructor makes the class DataWeave compatible.
    }

    public Map<String, String> getIndexSettings() {
        return indexSettings;
    }

    public String getIndexSettingsJSONFile() {
        return indexSettingsJSONFile;
    }

    public String getIndexMappingsJSONFile() {
        return indexMappingsJSONFile;
    }

    public String getIndexAlias() {
        return indexAlias;
    }

    public String getSourceJSONFile() {
        return sourceJSONFile;
    }

    public long getTimeoutInSec() {
        return timeoutInSec;
    }

    public long getMasterNodeTimeoutInSec() {
        return masterNodeTimeoutInSec;
    }

    public int getWaitForActiveShards() {
        return waitForActiveShards;
    }

}
