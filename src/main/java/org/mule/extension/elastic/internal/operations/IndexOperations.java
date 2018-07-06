/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.extension.elastic.internal.operations;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.close.CloseIndexRequest;
import org.elasticsearch.action.admin.indices.close.CloseIndexResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.Settings.Builder;
import org.elasticsearch.common.xcontent.XContentType;
import org.mule.extension.elastic.api.IndexOptions;
import org.mule.extension.elastic.internal.connection.ElasticsearchConnection;
import org.mule.extension.elastic.internal.utils.ElasticsearchUtils;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     * @param type
     *            The index type to define index
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
     * @param timeout
     *            Timeout to wait for the all the nodes to acknowledge the index creation
     * @param masterNodeTimeout
     *            Timeout to connect to the master node
	 * @param waitForActiveShards
     *            The number of active shard copies to wait for before the create index
     * @return CreateIndexResponse
     * @throws IOException throws IOException
     */

    @MediaType(value = ANY, strict = false)
    public CreateIndexResponse createIndex(@Connection ElasticsearchConnection esConnection,
            @Placement(order = 1) @DisplayName("Index") @Summary("The index to create") String index,
            @Placement(tab = "Optional Arguments", order = 1) @Optional @DisplayName("Index Settings") Map<String, String> indexSettings,
            @Placement(tab = "Optional Arguments", order = 2) @Optional @DisplayName("Index Settings JSON") String indexSettingFile,
            @Placement(tab = "Optional Arguments", order = 3) @Optional @DisplayName("Type for Mapping") String type,
            @Placement(tab = "Optional Arguments", order = 4) @Optional @DisplayName("Index Mapping") Map<String, Object> indexMapping,
            @Placement(tab = "Optional Arguments", order = 5) @Optional @DisplayName("JSON Input file path for mapping") @Summary("The whole source including all of its sections (mappings, settings and aliases) can be provided in this file") String jsonInputPath,
            @Placement(tab = "Optional Arguments", order = 6) @Optional @DisplayName("Index Alias") String indexAlias,
            @Placement(tab = "Optional Arguments", order = 7) @Optional @Summary("Timeout to wait for the all the nodes to acknowledge the index creation") @DisplayName("Timeout") String timeout,
            @Placement(tab = "Optional Arguments", order = 8) @Optional @Summary("Timeout to connect to the master node") @DisplayName("Master Node Timeout") String masterNodeTimeout,
            @Placement(tab = "Optional Arguments", order = 9) @DisplayName("Wait for Active Shards") @Optional(defaultValue = "0") int waitForActiveShards) throws IOException {

        CreateIndexRequest createIndexReq = new CreateIndexRequest(index);

        if (indexSettings != null) {
            final Builder settingsBuilder = Settings.builder();
            for (Map.Entry<String, String> entry : indexSettings.entrySet()) {
                settingsBuilder.put(entry.getKey(), entry.getValue());
            }
            createIndexReq.settings(settingsBuilder);
        }

        if (indexAlias != null) {
            createIndexReq.alias(new Alias(indexAlias));
        }

        if (jsonInputPath != null) {
            createIndexReq.mapping(type, ElasticsearchUtils.readFileToString(jsonInputPath), XContentType.JSON);
        } else if (indexMapping != null) {
            createIndexReq.mapping(type, indexMapping);

        }

        if (timeout != null) {
            createIndexReq.timeout(timeout);
        }

        if (masterNodeTimeout != null) {
            createIndexReq.masterNodeTimeout(masterNodeTimeout);

        }

        if (waitForActiveShards != 0) {
            createIndexReq.waitForActiveShards(waitForActiveShards);
        }

        CreateIndexResponse createIndexResp = esConnection.getElasticsearchConnection().indices().create(createIndexReq, ElasticsearchUtils.getContentTypeJsonHeader());
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
     * @param timeout
     *            Timeout to wait for the all the nodes to acknowledge the index deletion
     * @param masterNodeTimeout
     *            Timeout to connect to the master node
	 * @param indicesOpts
     *            IndicesOptions controls how unavailable indices are resolved and how wildcard expressions are expanded
     * @return DeleteIndexResponse
     * @throws IOException throws IOException
     */
    @MediaType(value = ANY, strict = false)
    public DeleteIndexResponse deleteIndex(@Connection ElasticsearchConnection esConnection,
            @Placement(order = 1) @DisplayName("Index") @Summary("The index to delete") String index,
            @Placement(tab = "Optional Arguments", order = 1) @Optional @Summary("Timeout to wait for the all the nodes to acknowledge the index creation") @DisplayName("Timeout") String timeout,
            @Placement(tab = "Optional Arguments", order = 2) @Optional @Summary("Timeout to connect to the master node") @DisplayName("Mater Node Timeout") String masterNodeTimeout,
            @Placement(tab = "Optional Arguments", order = 3) @Optional IndexOptions indicesOpts) throws IOException {

        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(index);

        if (timeout != null) {
            deleteIndexRequest.timeout(timeout);
        }

        if (masterNodeTimeout != null) {
            deleteIndexRequest.masterNodeTimeout(masterNodeTimeout);
        }

        if (indicesOpts != null) {
            IndicesOptions indOptions = IndicesOptions.fromOptions(indicesOpts.isIgnoreUnavailable(), indicesOpts.isAllowNoIndices(), indicesOpts.isExpandToOpenIndices(),
                    indicesOpts.isExpandToClosedIndices(), indicesOpts.isAllowAliasesToMultipleIndices(), indicesOpts.isForbidClosedIndices(), indicesOpts.isIgnoreAliases());
            deleteIndexRequest.indicesOptions(indOptions);
        }

        DeleteIndexResponse deleteIndexResp = esConnection.getElasticsearchConnection().indices().delete(deleteIndexRequest, ElasticsearchUtils.getContentTypeJsonHeader());
        logger.info("Delete Index Response : " + deleteIndexResp);
        return deleteIndexResp;
    }

    /**
     * Open Index operation allow to open an index.
     *
	 * @param esConnection
     *            The Elasticsearch connection
     * @param index
     *            The index to open
     * @param timeout
     *            Timeout to wait for the all the nodes to acknowledge the index is opened. It is the time to wait for an open index to become available to elasticsearch.
     * @param masterNodeTimeout
     *            Timeout to connect to the master node
	 * @param waitForActiveShards
     *            The number of active shard copies to wait for
	 * @param indicesOpts
     *            IndicesOptions controls how unavailable indices are resolved and how wildcard expressions are expanded
     * @return OpenIndexResponse
     * @throws IOException throws IOException
     */
    @MediaType(value = ANY, strict = false)
    public OpenIndexResponse openIndex(@Connection ElasticsearchConnection esConnection, @Placement(order = 1) @DisplayName("Index") @Summary("The index to open") String index,
            @Placement(tab = "Optional Arguments", order = 1) @Optional @Summary("Timeout to wait for the all the nodes to acknowledge the index creation") @DisplayName("Timeout") String timeout,
            @Placement(tab = "Optional Arguments", order = 2) @Optional @Summary("Timeout to connect to the master node") @DisplayName("Mater Node Timeout") String masterNodeTimeout,
            @Placement(tab = "Optional Arguments", order = 3) @DisplayName("Wait for Active Shards") @Optional(defaultValue = "0") int waitForActiveShards,
            @Placement(tab = "Optional Arguments", order = 4) @Optional IndexOptions indicesOpts) throws IOException {

        OpenIndexRequest openIndexRequest = new OpenIndexRequest(index);

        if (timeout != null) {
            openIndexRequest.timeout(timeout);
        }

        if (masterNodeTimeout != null) {
            openIndexRequest.masterNodeTimeout(masterNodeTimeout);
        }

        if (waitForActiveShards != 0) {

            openIndexRequest.waitForActiveShards(waitForActiveShards);
        }

        if (indicesOpts != null) {
            IndicesOptions indOptions = IndicesOptions.fromOptions(indicesOpts.isIgnoreUnavailable(), indicesOpts.isAllowNoIndices(), indicesOpts.isExpandToOpenIndices(),
                    indicesOpts.isExpandToClosedIndices(), indicesOpts.isAllowAliasesToMultipleIndices(), indicesOpts.isForbidClosedIndices(), indicesOpts.isIgnoreAliases());
            openIndexRequest.indicesOptions(indOptions);
        }

        OpenIndexResponse openIndexResp = esConnection.getElasticsearchConnection().indices().open(openIndexRequest, ElasticsearchUtils.getContentTypeJsonHeader());
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
     * @param timeout
     *            Time to wait for the all the nodes to acknowledge if the index is closed
     * @param masterNodeTimeout
     *            Timeout to connect to the master node
	 * @param indicesOpt
     *            IndicesOptions controls how unavailable indices are resolved and how wildcard expressions are expanded
     * @return CloseIndexResponse
     * @throws IOException throws IOException
     */
    @MediaType(value = ANY, strict = false)
    public CloseIndexResponse closeIndex(@Connection ElasticsearchConnection esConnection, @Placement(order = 1) @DisplayName("Index") @Summary("The index to open") String index,
            @Placement(tab = "Optional Arguments", order = 1) @Optional @Summary("Timeout to wait for the all the nodes to acknowledge the index creation") @DisplayName("Timeout") String timeout,
            @Placement(tab = "Optional Arguments", order = 2) @Optional @Summary("Timeout to connect to the master node") @DisplayName("Mater Node Timeout") String masterNodeTimeout,
            @Placement(tab = "Optional Arguments", order = 3) @Optional IndexOptions indicesOpt) throws IOException {

        CloseIndexRequest closeIndexRequest = new CloseIndexRequest(index);

        if (timeout != null) {
            closeIndexRequest.timeout(timeout);
        }
        if (masterNodeTimeout != null) {
            closeIndexRequest.masterNodeTimeout(masterNodeTimeout);
        }
        if (indicesOpt != null) {
            IndicesOptions indOptions = IndicesOptions.fromOptions(indicesOpt.isIgnoreUnavailable(), indicesOpt.isAllowNoIndices(), indicesOpt.isExpandToOpenIndices(),
                    indicesOpt.isExpandToClosedIndices(), indicesOpt.isAllowAliasesToMultipleIndices(), indicesOpt.isForbidClosedIndices(), indicesOpt.isIgnoreAliases());
            closeIndexRequest.indicesOptions(indOptions);
        }

        CloseIndexResponse closeIndexResp = esConnection.getElasticsearchConnection().indices().close(closeIndexRequest, ElasticsearchUtils.getContentTypeJsonHeader());
        logger.info("Close Index Response : " + closeIndexResp);
        return closeIndexResp;
    }

}
