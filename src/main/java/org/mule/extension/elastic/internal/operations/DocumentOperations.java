/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.extension.elastic.internal.operations;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import java.util.Collections;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.action.DocWriteRequest.OpType;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Response;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.VersionType;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.mule.extension.elastic.api.DocumentFetchSourceOptions;
import org.mule.extension.elastic.api.IndexDocumentOptions;
import org.mule.extension.elastic.api.JsonData;
import org.mule.extension.elastic.internal.connection.ElasticsearchConnection;
import org.mule.extension.elastic.internal.error.ElasticsearchError;
import org.mule.extension.elastic.internal.error.exception.ElasticsearchException;
import org.mule.extension.elastic.internal.utils.ElasticsearchUtils;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 *
 */
public class DocumentOperations {

    /**
     * Logging object
     */
    private static final Logger logger = LoggerFactory.getLogger(IndexOperations.class);

    /**
     * Index Document operation adds or updates a typed JSON document in a specific index, making it searchable.
     * 
     * @param esConnection
     *            The Elasticsearch connection
     * @param index
     *            Name of the index
     * @param type
     *            Type of the index
     * @param documentId
     *            ID of the document
     * @param routing
     *            Routing is used to determine in which shard the document will reside in
     * @param inputSource
     *            Get the JSON input file path or index mapping.
     * @param parent
     *            A parent-child relationship can be established between documents in the same index by making one mapping type the parent of another
     * @param timeout
     *            Timeout to wait for primary shard to become available
     * @param refreshPolicy
     *            Refresh policy is used to control when changes made by the requests are made visible to search. Option for refresh policy A) true : Refresh the relevant primary
     *            and replica shards (not the whole index) immediately after the operation occurs, so that the updated document appears in search results immediately. B) wait_for :
     *            Wait for the changes made by the request to be made visible by a refresh before replying. This doesn’t force an immediate refresh, rather, it waits for a refresh
     *            to happen. C) false (default) : Take no refresh related actions. The changes made by this request will be made visible at some point after the request returns.
     * @param version
     *            Version number of the indexed document. It will control the version of the document the operation is intended to be executed against.
     * @param versionType
     *            Version type: internal, external, external_gte
     * @param operationType
     *            Type of the operation. When create type is used, the index operation will fail if a document by that id already exists in the index.
     * @param pipeline
     *            Name of the ingest pipeline to be executed before indexing the document
     * @return IndexResponse
     */

    @MediaType(value = ANY, strict = false)
    public IndexResponse indexDocument(@Connection ElasticsearchConnection esConnection, @Placement(order = 1) @DisplayName("Index") String index,
            @Placement(order = 2) @DisplayName("Type") String type, @Placement(order = 3) @DisplayName("Document Id") String documentId,
            @Placement(order = 4) @ParameterGroup(name = "Input Document") IndexDocumentOptions inputSource,
            @Placement(tab = "Optional Arguments", order = 1) @DisplayName("Routing") @Optional String routing,
            @Placement(tab = "Optional Arguments", order = 2) @DisplayName("Parent") @Optional String parent,
            @Placement(tab = "Optional Arguments", order = 3) @DisplayName("Timeout") @Optional @Summary("Timeout to wait for primary shard") String timeout,
            @Placement(tab = "Optional Arguments", order = 4) @DisplayName("Refresh policy") @Optional RefreshPolicy refreshPolicy,
            @Placement(tab = "Optional Arguments", order = 5) @DisplayName("Version") @Optional(defaultValue = "0") long version,
            @Placement(tab = "Optional Arguments", order = 6) @DisplayName("Version Type") @Optional VersionType versionType,
            @Placement(tab = "Optional Arguments", order = 7) @DisplayName("Operation type") @Optional OpType operationType,
            @Placement(tab = "Optional Arguments", order = 8) @DisplayName("Pipeline") @Optional @Summary("The name of the ingest pipeline to be executed before indexing the document") String pipeline) {

        IndexRequest indexRequest;
        try {
            if (inputSource.getJsonInputPath() != null) {
                indexRequest = new IndexRequest(index, type, documentId).source(ElasticsearchUtils.readFileToString(inputSource.getJsonInputPath()), XContentType.JSON);
            } else {
                indexRequest = new IndexRequest(index, type, documentId).source(inputSource.getIndexMapping());
            }
            if (routing != null) {
                indexRequest.routing(routing);
            }
            if (parent != null) {
                indexRequest.parent(parent);
            }
            if (timeout != null) {
                indexRequest.timeout(timeout);
            }
            if (refreshPolicy != null) {
                indexRequest.setRefreshPolicy(refreshPolicy);
            }
            if (version != 0) {
                indexRequest.version(version);
            }
            if (versionType != null) {
                indexRequest.versionType(versionType);
            }
            if (operationType != null) {
                indexRequest.opType(operationType);
            }
            if (pipeline != null) {
                indexRequest.setPipeline(pipeline);
            }

            IndexResponse indexResp = esConnection.getElasticsearchConnection().index(indexRequest, ElasticsearchUtils.getContentTypeJsonHeader());

            logger.info("Index Response : " + indexResp);
            return indexResp;
        } catch (Exception e) {
            throw new ElasticsearchException(ElasticsearchError.OPERATION_FAILED, e);
        }
    }

    /**
     * Get Document operation allows to get a typed JSON document from the index based on its id.
     * 
     * @param esConnection
     *            The Elasticsearch connection
     * @param index
     *            Name of the index
     * @param type
     *            Type of the index
     * @param documentId
     *            ID of the document
     * @param fetchSourceContext
     *            Enable or disable source retrieval
     * @param realtime
     *            Set realtime flag
     * @param routing
     *            Routing is used to determine in which shard the document will reside in
     * @param parent
     *            Parent value of the index request
     * @param preference
     *            Preference value
     * @param refresh
     *            Perform a refresh before retrieving the document
     * @param version
     *            Version number of the indexed document
     * @param versionType
     *            Version type: internal, external, external_gte,
     * @return Result that includes the index, type, id and version of the document
     */

    @MediaType(value = ANY, strict = false)
    public String getDocument(@Connection ElasticsearchConnection esConnection, @Placement(order = 1) @DisplayName("Index") String index,
            @Placement(order = 2) @DisplayName("Type") String type, @Placement(order = 3) @DisplayName("Document Id") String documentId,
            @Placement(tab = "Optional Arguments", order = 1) @DisplayName("Source retrieval") @Optional DocumentFetchSourceOptions fetchSourceContext,
            @Placement(tab = "Optional Arguments", order = 2) @DisplayName("Routing") @Optional String routing,
            @Placement(tab = "Optional Arguments", order = 3) @DisplayName("Parent") @Optional String parent,
            @Placement(tab = "Optional Arguments", order = 4) @DisplayName("Preference value") @Optional String preference,
            @Placement(tab = "Optional Arguments", order = 5) @DisplayName("Set realtime flag") @Optional(defaultValue = "true") boolean realtime,
            @Placement(tab = "Optional Arguments", order = 6) @DisplayName("Refresh") @Summary("Perform a refresh before retrieving the document") @Optional(defaultValue = "false") boolean refresh,
            @Placement(tab = "Optional Arguments", order = 7) @DisplayName("Version") @Optional(defaultValue = "0") long version,
            @Placement(tab = "Optional Arguments", order = 8) @DisplayName("Version Type") @Optional VersionType versionType) {

        GetRequest getRequest = new GetRequest(index, type, documentId);
        if (fetchSourceContext != null && fetchSourceContext.isFetchSource()) {

            String[] includes = Strings.EMPTY_ARRAY, excludes = Strings.EMPTY_ARRAY;

            if (fetchSourceContext.getIncludeFields() != null) {
                includes = fetchSourceContext.getIncludeFields().toArray(new String[0]);
            }

            if (fetchSourceContext.getExcludeFields() != null) {
                excludes = fetchSourceContext.getExcludeFields().toArray(new String[0]);
            }

            FetchSourceContext fetchSource = new FetchSourceContext(true, includes, excludes);
            getRequest.fetchSourceContext(fetchSource);

        }

        if (routing != null) {
            getRequest.routing(routing);
        }
        if (parent != null) {
            getRequest.parent(parent);
        }
        if (preference != null) {
            getRequest.preference(preference);
        }
        if (version != 0) {
            logger.info("Version in get Index document : " + version);
            getRequest.version(version);
        }
        if (versionType != null) {
            getRequest.versionType(versionType);
        }

        getRequest.realtime(realtime);
        getRequest.refresh(refresh);
        GetResponse getResp;
        try {
            getResp = esConnection.getElasticsearchConnection().get(getRequest, ElasticsearchUtils.getContentTypeJsonHeader());
        } catch (Exception e) {
            throw new ElasticsearchException(ElasticsearchError.OPERATION_FAILED, e);
        }
        logger.info("Get Response : " + getResp);
        return getResp.getSourceAsString();
    }

    /**
     * Delete Document operation allows to delete a typed JSON document from a specific index based on its id
     * 
     * @param esConnection
     *            The Elasticsearch connection
     * @param index
     *            Name of the index
     * @param type
     *            Type of the index
     * @param documentId
     *            ID of the document
     * @param routing
     *            Routing is used to determine in which shard the document will reside in
     * @param parent
     *            Parent value of the index request
     * @param timeout
     *            Time to wait for primary shard to become available
     * @param refreshPolicy
     *            Refresh policy is used to control when changes made by the requests are made visible to search. Option for refresh policy A) true : Refresh the relevant primary
     *            and replica shards (not the whole index) immediately after the operation occurs, so that the updated document appears in search results immediately. B) wait_for :
     *            Wait for the changes made by the request to be made visible by a refresh before replying. This doesn’t force an immediate refresh, rather, it waits for a refresh
     *            to happen. C) false (default) : Take no refresh related actions. The changes made by this request will be made visible at some point after the request returns.
     * @param version
     *            Version number of the indexed document
     * @param versionType
     *            Version type: internal, external, external_gte
     * @return DeleteResponse
     */
    @MediaType(value = ANY, strict = false)
    public DeleteResponse deleteDocument(@Connection ElasticsearchConnection esConnection, @Placement(order = 1) @DisplayName("Index") String index,
            @Placement(order = 2) @DisplayName("Type") String type, @Placement(order = 3) @DisplayName("Document Id") String documentId,
            @Placement(tab = "Optional Arguments", order = 1) @DisplayName("Routing value") @Optional String routing,
            @Placement(tab = "Optional Arguments", order = 2) @DisplayName("Parent value") @Optional String parent,
            @Placement(tab = "Optional Arguments", order = 3) @DisplayName("Timeout") @Optional @Summary("Timeout to wait for primary shard") String timeout,
            @Placement(tab = "Optional Arguments", order = 4) @DisplayName("Refresh policy") @Optional RefreshPolicy refreshPolicy,
            @Placement(tab = "Optional Arguments", order = 5) @DisplayName("Version") @Optional(defaultValue = "0") long version,
            @Placement(tab = "Optional Arguments", order = 6) @DisplayName("Version Type") @Optional VersionType versionType) {

        DeleteRequest deleteRequest = new DeleteRequest(index, type, documentId);
        if (routing != null) {
            deleteRequest.routing(routing);
        }
        if (parent != null) {
            deleteRequest.parent(parent);
        }
        if (timeout != null) {
            deleteRequest.timeout(timeout);
        }
        if (refreshPolicy != null) {
            deleteRequest.setRefreshPolicy(refreshPolicy);
        }
        if (version != 0) {
            deleteRequest.version(version);
        }

        DeleteResponse deleteResp;
        try {
            deleteResp = esConnection.getElasticsearchConnection().delete(deleteRequest, ElasticsearchUtils.getContentTypeJsonHeader());
        } catch (Exception e) {
            throw new ElasticsearchException(ElasticsearchError.OPERATION_FAILED, e);
        }
        logger.info("Delete document response : " + deleteResp);
        return deleteResp;
    }

    /**
     * Update Document operation allows to update a document based on a script provided.
     * 
     * @param esConnection
     *            The Elasticsearch connection
     * @param index
     *            Name of the index
     * @param type
     *            Type of the index
     * @param documentId
     *            ID of the document
     * @param routing
     *            Routing is used to determine in which shard the document will reside in
     * @param inputSource
     *            Input document source
     * @param parent
     *            Parent value of the index request
     * @param timeout
     *            Time to wait for primary shard to become available
     * @param refreshPolicy
     *            Refresh policy is used to control when changes made by the requests are made visible to search. Option for refresh policy A) true : Refresh the relevant primary
     *            and replica shards (not the whole index) immediately after the operation occurs, so that the updated document appears in search results immediately. B) wait_for :
     *            Wait for the changes made by the request to be made visible by a refresh before replying. This doesn’t force an immediate refresh, rather, it waits for a refresh
     *            to happen. C) false (default) : Take no refresh related actions. The changes made by this request will be made visible at some point after the request returns.
     * @param retryOnConflict
     *            How many times to retry the update operation if the document to update has been changed by another operation between the get and indexing phases of the update
     *            operation
     * @param fetchSource
     *            Enable or disable source retrieval
     * @param version
     *            Version number of the indexed document
     * @param detectNoop
     *            Enable or disable the noop detection
     * @param scriptedUpsert
     *            Indicate that the script must run regardless of whether the document exists or not
     * @param docAsUpsert
     *            Indicate that the partial document must be used as the upsert document if it does not exist yet.
     * @return UpdateResponse
     */
    @MediaType(value = ANY, strict = false)
    public UpdateResponse updateDocument(@Connection ElasticsearchConnection esConnection, @Placement(order = 1) @DisplayName("Index") String index,
            @Placement(order = 2) @DisplayName("Type") String type, @Placement(order = 3) @DisplayName("Document Id") String documentId,
            @Placement(order = 4) @ParameterGroup(name = "Input Document") IndexDocumentOptions inputSource,
            @Placement(tab = "Optional Arguments", order = 1) @DisplayName("Routing") @Optional String routing,
            @Placement(tab = "Optional Arguments", order = 2) @DisplayName("Parent") @Optional String parent,
            @Placement(tab = "Optional Arguments", order = 3) @DisplayName("Timeout") @Optional @Summary("Timeout to wait for primary shard") String timeout,
            @Placement(tab = "Optional Arguments", order = 4) @DisplayName("Refresh policy") @Optional RefreshPolicy refreshPolicy,
            @Placement(tab = "Optional Arguments", order = 5) @DisplayName("Retry on Conflict") @Optional(defaultValue = "0") @Summary("How many times to retry the update operation if the document to update has been changed by another operation between the get and indexing phases of the update operation") int retryOnConflict,
            @Placement(tab = "Optional Arguments", order = 6) @DisplayName("Fetch Source") @Optional(defaultValue = "false") boolean fetchSource,
            @Placement(tab = "Optional Arguments", order = 7) @DisplayName("Version") @Optional(defaultValue = "0") long version,
            @Placement(tab = "Optional Arguments", order = 8) @DisplayName("Noop Detection") @Optional(defaultValue = "true") boolean detectNoop,
            @Placement(tab = "Optional Arguments", order = 9) @DisplayName("Scripted Upsert") @Optional(defaultValue = "false") boolean scriptedUpsert,
            @Placement(tab = "Optional Arguments", order = 10) @DisplayName("Doc Upsert") @Optional(defaultValue = "false") boolean docAsUpsert) {

        UpdateRequest updateRequest = new UpdateRequest(index, type, documentId);
        if (inputSource.getJsonInputPath() != null) {
            try {
                updateRequest.doc(ElasticsearchUtils.readFileToString(inputSource.getJsonInputPath()), XContentType.JSON);
            } catch (Exception e) {
                throw new ElasticsearchException(ElasticsearchError.OPERATION_FAILED, e);
            }
        } else {
            updateRequest.doc(inputSource.getIndexMapping());

        }

        if (routing != null) {
            updateRequest.routing(routing);
        }
        if (parent != null) {
            updateRequest.parent(parent);
        }
        if (timeout != null) {
            updateRequest.timeout(timeout);
        }
        if (refreshPolicy != null) {
            updateRequest.setRefreshPolicy(refreshPolicy);
        }
        if (retryOnConflict != 0) {
            updateRequest.retryOnConflict(retryOnConflict);
        }
        if (version != 0L) {
            updateRequest.version(version);
        }
        updateRequest.fetchSource(fetchSource);
        updateRequest.detectNoop(detectNoop);
        updateRequest.scriptedUpsert(scriptedUpsert);
        updateRequest.docAsUpsert(docAsUpsert);
        UpdateResponse updateResp;
        try {
            updateResp = esConnection.getElasticsearchConnection().update(updateRequest, ElasticsearchUtils.getContentTypeJsonHeader());
        } catch (Exception e) {
            throw new ElasticsearchException(ElasticsearchError.OPERATION_FAILED, e);
        }
        logger.info("Update Response : " + updateResp);
        return updateResp;
    }

    /**
     * Bulk operation makes it possible to perform many index, delete and update operations in a single API call.
     * 
     * @param esConnection
     *            The Elasticsearch connection
     * @param index
     *            Index name on which bulk operation Performed
     * 
     * @param type
     *            Type name on which bulk operation Performed
     * 
     * @param jsonData
     *            Input file / data with list of operations to be performed like index, delete, update.
     * @return Response
     */

    @MediaType(value = ANY, strict = false)
    public Response bulkOperation(@Connection ElasticsearchConnection esConnection, @Optional String index, @Optional String type,
            @ParameterGroup(name = "Input data") JsonData jsonData) {
        String resource = type != null ? "/" + type + "/_bulk" : "/_bulk";
        resource = index != null ? "/" + index + resource : resource;
        Map<String, String> params = Collections.singletonMap("pretty", "true");
        HttpEntity entity;
        try {
            if (jsonData.getJsonfile() != null) {
                String jsonContent;
                jsonContent = ElasticsearchUtils.readFileToString(jsonData.getJsonfile());
                entity = new NStringEntity(jsonContent, ContentType.APPLICATION_JSON);
            } else {
                entity = new NStringEntity(jsonData.getJsonText(), ContentType.APPLICATION_JSON);
            }

            return esConnection.getElasticsearchConnection().getLowLevelClient().performRequest("POST", resource, params, entity);
        } catch (Exception e) {
            throw new ElasticsearchException(ElasticsearchError.OPERATION_FAILED, e);
        }
    }
}