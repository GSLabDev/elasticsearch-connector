/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.internal.operations;

import static com.mulesoft.connectors.elasticsearch.internal.utils.ElasticsearchUtils.ifPresent;
import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import java.util.Map;

import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.client.indices.CloseIndexRequest;
import org.elasticsearch.client.indices.CloseIndexResponse;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.Settings.Builder;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mulesoft.connectors.elasticsearch.api.IndexOptions;
import com.mulesoft.connectors.elasticsearch.internal.connection.ElasticsearchConnection;
import com.mulesoft.connectors.elasticsearch.internal.error.ElasticsearchError;
import com.mulesoft.connectors.elasticsearch.internal.error.exception.ElasticsearchException;
import com.mulesoft.connectors.elasticsearch.internal.utils.ElasticsearchUtils;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 *
 */

public class IndexOperations {

    /**
     * Logging object
     */
    private static final Logger logger = LoggerFactory.getLogger(IndexOperations.class);

    /**
     * The createIndex Operation allows to instantiate an index.
     *
     * @param esConnection
     *            The Elasticsearch connection
     * @param index
     *            The index to create
     * @param indexSettings
     *            Settings for this index
     * @param indexSettingFile
     *            Index Settings JSON
     * @param indexMapping
     *            The mapping for index type, provided as a JSON string
     * @param indexAlias
     *            The alias of the index
     * @param jsonInputPath
     *            Path of the JSON file. The whole source including all of its sections (mappings, settings and aliases) can be provided in this json file.
     * @param timeoutInSec
     *            Timeout in seconds to wait for the all the nodes to acknowledge the index creation
     * @param masterNodeTimeoutInSec
     *            Timeout in seconds to connect to the master node
     * @param waitForActiveShards
     *            The number of active shard copies to wait for before the create index
     * @return CreateIndexResponse
     * 
     */
    @MediaType(value = ANY, strict = false)
    @DisplayName("Index - Create")
    public CreateIndexResponse createIndex(@Connection ElasticsearchConnection esConnection,
            @Placement(order = 1) @DisplayName("Index") @Summary("The index to create") String index,
            @Placement(tab = "Optional Arguments", order = 1) @Optional @DisplayName("Index Settings") Map<String, String> indexSettings,
            @Placement(tab = "Optional Arguments", order = 2) @Optional @DisplayName("Index Settings JSON") String indexSettingFile,
            @Placement(tab = "Optional Arguments", order = 4) @Optional @DisplayName("Index Mapping") Map<String, Object> indexMapping,
            @Placement(tab = "Optional Arguments", order = 5) @Optional @DisplayName("JSON Input file path for mapping") @Summary("The whole source including all of its sections (mappings, settings and aliases) can be provided in this file") String jsonInputPath,
            @Placement(tab = "Optional Arguments", order = 6) @Optional @DisplayName("Index Alias") String indexAlias,
            @Placement(tab = "Optional Arguments", order = 7) @Optional(defaultValue = "0") @Summary("Timeout in seconds to wait for the all the nodes to acknowledge the index creation") @DisplayName("Timeout (Seconds)") long timeoutInSec,
            @Placement(tab = "Optional Arguments", order = 8) @Optional(defaultValue = "0") @Summary("Timeout in seconds to connect to the master node") @DisplayName("Master Node Timeout (Seconds)") long masterNodeTimeoutInSec,
            @Placement(tab = "Optional Arguments", order = 9) @DisplayName("Wait for Active Shards") @Optional(defaultValue = "0") int waitForActiveShards) {

        CreateIndexRequest createIndexReq = new CreateIndexRequest(index);

        if (indexSettings != null) {
            final Builder settingsBuilder = Settings.builder();
            for (Map.Entry<String, String> entry : indexSettings.entrySet()) {
                settingsBuilder.put(entry.getKey(), entry.getValue());
            }
            createIndexReq.settings(settingsBuilder);
        }

        if (jsonInputPath != null) {
            try {
                createIndexReq.mapping(ElasticsearchUtils.readFileToString(jsonInputPath), XContentType.JSON);
            } catch (Exception e) {
                throw new ElasticsearchException(ElasticsearchError.OPERATION_FAILED, e);
            }
        } else if (indexMapping != null) {
            createIndexReq.mapping(indexMapping);

        }

        ifPresent(indexAlias, indexAliasValue -> createIndexReq.alias(new Alias(indexAliasValue)));
        if (timeoutInSec != 0) {
            createIndexReq.setTimeout(TimeValue.timeValueSeconds(timeoutInSec));
        }
        if (masterNodeTimeoutInSec != 0) {
            createIndexReq.setMasterTimeout(TimeValue.timeValueSeconds(masterNodeTimeoutInSec));
        }
        
        if (waitForActiveShards != 0) {
            createIndexReq.waitForActiveShards(ActiveShardCount.from(waitForActiveShards));
        }

        CreateIndexResponse createIndexResp;
        try {
            createIndexResp = esConnection.getElasticsearchConnection().indices().create(createIndexReq, ElasticsearchUtils.getContentTypeJsonRequestOption());
        } catch (Exception e) {
            throw new ElasticsearchException(ElasticsearchError.OPERATION_FAILED, e);
        }
        logger.info("Create Index Response : ", createIndexResp);
        return createIndexResp;
    }

    /**
     * The Delete index operation allows to delete an existing index.
     *
     * @param esConnection
     *            The Elasticsearch connection
     * @param index
     *            The index to delete
     * @param timeoutInSec
     *            Timeout in seconds to wait for the all the nodes to acknowledge the index deletion
     * @param masterNodeTimeoutInSec
     *            Timeout in seconds to connect to the master node
     * @param indicesOpts
     *            IndicesOptions controls how unavailable indices are resolved and how wildcard expressions are expanded
     * @return DeleteIndexResponse
     * 
     */
    @MediaType(value = ANY, strict = false)
    @DisplayName("Index - Delete")
    public AcknowledgedResponse deleteIndex(@Connection ElasticsearchConnection esConnection,
            @Placement(order = 1) @DisplayName("Index") @Summary("The index to delete") String index,
            @Placement(tab = "Optional Arguments", order = 1) @Optional(defaultValue = "0") @Summary("Timeout in seconds to wait for the all the nodes to acknowledge the index creation") @DisplayName("Timeout (Seconds)") long timeoutInSec,
            @Placement(tab = "Optional Arguments", order = 2) @Optional(defaultValue = "0") @Summary("Timeout in seconds to connect to the master node") @DisplayName("Mater Node Timeout (Seconds)") long masterNodeTimeoutInSec,
            @Placement(tab = "Optional Arguments", order = 3) @Optional IndexOptions indicesOpts) {

        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(index);

        if (timeoutInSec != 0) {
            deleteIndexRequest.timeout(TimeValue.timeValueSeconds(timeoutInSec));
        }
        if (masterNodeTimeoutInSec != 0) {
            deleteIndexRequest.masterNodeTimeout(TimeValue.timeValueSeconds(masterNodeTimeoutInSec));
        }
        
        if (indicesOpts != null) {
            IndicesOptions indOptions = IndicesOptions.fromOptions(indicesOpts.isIgnoreUnavailable(), indicesOpts.isAllowNoIndices(), indicesOpts.isExpandToOpenIndices(),
                    indicesOpts.isExpandToClosedIndices(), indicesOpts.isAllowAliasesToMultipleIndices(), indicesOpts.isForbidClosedIndices(), indicesOpts.isIgnoreAliases(),
                    indicesOpts.isIgnoreThrottled());
            deleteIndexRequest.indicesOptions(indOptions);
        }

        AcknowledgedResponse response;
        try {
            response = esConnection.getElasticsearchConnection().indices().delete(deleteIndexRequest, ElasticsearchUtils.getContentTypeJsonRequestOption());
        } catch (Exception e) {
            throw new ElasticsearchException(ElasticsearchError.OPERATION_FAILED, e);
        }
        logger.info("Delete Index Response : " + response);
        return response;
    }

    /**
     * Open Index operation allow to open an index.
     *
     * @param esConnection
     *            The Elasticsearch connection
     * @param index
     *            The index to open
     * @param timeoutInSec
     *            Timeout in seconds to wait for the all the nodes to acknowledge the index is opened. It is the time to wait for an open index to become available to elasticsearch.
     * @param masterNodeTimeoutInSec
     *            Timeout in seconds to connect to the master node
     * @param waitForActiveShards
     *            The number of active shard copies to wait for
     * @param indicesOpts
     *            IndicesOptions controls how unavailable indices are resolved and how wildcard expressions are expanded
     * @return OpenIndexResponse
     * 
     */
    @MediaType(value = ANY, strict = false)
    @DisplayName("Index - Open")
    public OpenIndexResponse openIndex(@Connection ElasticsearchConnection esConnection, @Placement(order = 1) @DisplayName("Index") @Summary("The index to open") String index,
            @Placement(tab = "Optional Arguments", order = 1) @Optional(defaultValue = "0") @Summary("Timeout in seconds to wait for the all the nodes to acknowledge the index creation") @DisplayName("Timeout (Seconds)") long timeoutInSec,
            @Placement(tab = "Optional Arguments", order = 2) @Optional(defaultValue = "0") @Summary("Timeout in seconds to connect to the master node") @DisplayName("Mater Node Timeout (Seconds)") long masterNodeTimeoutInSec,
            @Placement(tab = "Optional Arguments", order = 3) @DisplayName("Wait for Active Shards") @Optional(defaultValue = "0") int waitForActiveShards,
            @Placement(tab = "Optional Arguments", order = 4) @Optional IndexOptions indicesOpts) {

        OpenIndexRequest openIndexRequest = new OpenIndexRequest(index);

        if (timeoutInSec != 0) {
            openIndexRequest.timeout(TimeValue.timeValueSeconds(timeoutInSec));
        }
        if (masterNodeTimeoutInSec != 0) {
            openIndexRequest.masterNodeTimeout(TimeValue.timeValueSeconds(masterNodeTimeoutInSec));
        }
        
        if (waitForActiveShards != 0) {
            openIndexRequest.waitForActiveShards(waitForActiveShards);
        }

        if (indicesOpts != null) {
            IndicesOptions indOptions = IndicesOptions.fromOptions(indicesOpts.isIgnoreUnavailable(), indicesOpts.isAllowNoIndices(), indicesOpts.isExpandToOpenIndices(),
                    indicesOpts.isExpandToClosedIndices(), indicesOpts.isAllowAliasesToMultipleIndices(), indicesOpts.isForbidClosedIndices(), indicesOpts.isIgnoreAliases(),
                    indicesOpts.isIgnoreThrottled());
            openIndexRequest.indicesOptions(indOptions);
        }

        OpenIndexResponse openIndexResp;
        try {
            openIndexResp = esConnection.getElasticsearchConnection().indices().open(openIndexRequest, ElasticsearchUtils.getContentTypeJsonRequestOption());
        } catch (Exception e) {
            throw new ElasticsearchException(ElasticsearchError.OPERATION_FAILED, e);
        }
        logger.info("Open Index Response : " + openIndexResp);
        return openIndexResp;

    }

    /**
     * A closed index has almost no overhead. It is used to close an Index. If you want to keep your data but save resources (memory/CPU), a good alternative to deleting an index
     * is to close them.
     *
     * @param esConnection
     *            The Elasticsearch connection
     * @param index
     *            The index to close
     * @param timeoutInSec
     *            Timeout in seconds to wait for the all the nodes to acknowledge if the index is closed
     * @param masterNodeTimeoutInSec
     *            Timeout in seconds to connect to the master node
     * @param indicesOpt
     *            IndicesOptions controls how unavailable indices are resolved and how wildcard expressions are expanded
     * @return CloseIndexResponse
     * 
     */
    @MediaType(value = ANY, strict = false)
    @DisplayName("Index - Close")
    public CloseIndexResponse closeIndex(@Connection ElasticsearchConnection esConnection, @Placement(order = 1) @DisplayName("Index") @Summary("The index to open") String index,
            @Placement(tab = "Optional Arguments", order = 1) @Optional(defaultValue = "0") @Summary("Timeout in seconds to wait for the all the nodes to acknowledge the index creation") @DisplayName("Timeout (Seconds)") long timeoutInSec,
            @Placement(tab = "Optional Arguments", order = 2) @Optional(defaultValue = "0") @Summary("Timeout in seconds to connect to the master node") @DisplayName("Mater Node Timeout (Seconds)") long masterNodeTimeoutInSec,
            @Placement(tab = "Optional Arguments", order = 3) @Optional IndexOptions indicesOpt) {

        CloseIndexRequest closeIndexRequest = new CloseIndexRequest(index);

        if (timeoutInSec != 0) {
            closeIndexRequest.setTimeout(TimeValue.timeValueSeconds(timeoutInSec));
        }
        if (masterNodeTimeoutInSec != 0) {
            closeIndexRequest.setMasterTimeout(TimeValue.timeValueSeconds(masterNodeTimeoutInSec));
        }
        
        if (indicesOpt != null) {
            IndicesOptions indOptions = IndicesOptions.fromOptions(indicesOpt.isIgnoreUnavailable(), indicesOpt.isAllowNoIndices(), indicesOpt.isExpandToOpenIndices(),
                    indicesOpt.isExpandToClosedIndices(), indicesOpt.isAllowAliasesToMultipleIndices(), indicesOpt.isForbidClosedIndices(), indicesOpt.isIgnoreAliases(),
                    indicesOpt.isIgnoreThrottled());
            closeIndexRequest.indicesOptions(indOptions);
        }

        CloseIndexResponse closeIndexResp;
        try {
            closeIndexResp = esConnection.getElasticsearchConnection().indices().close(closeIndexRequest, ElasticsearchUtils.getContentTypeJsonRequestOption());
        } catch (Exception e) {
            throw new ElasticsearchException(ElasticsearchError.OPERATION_FAILED, e);
        }
        logger.info("Close Index Response : " + closeIndexResp);
        return closeIndexResp;
    }

}
