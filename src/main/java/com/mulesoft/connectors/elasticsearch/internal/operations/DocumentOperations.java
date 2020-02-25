/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.internal.operations;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;
import static com.mulesoft.connectors.elasticsearch.internal.utils.ElasticsearchUtils.ifPresent;

import java.io.IOException;
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
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.VersionType;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.mule.runtime.extension.api.runtime.process.CompletionCallback;
import org.apache.log4j.Logger;

import com.mulesoft.connectors.elasticsearch.api.DocumentFetchSourceOptions;
import com.mulesoft.connectors.elasticsearch.api.IndexDocumentOptions;
import com.mulesoft.connectors.elasticsearch.api.JsonData;
import com.mulesoft.connectors.elasticsearch.internal.connection.ElasticsearchConnection;
import com.mulesoft.connectors.elasticsearch.internal.error.ElasticsearchErrorTypes;
import com.mulesoft.connectors.elasticsearch.internal.error.exception.ElasticsearchException;
import com.mulesoft.connectors.elasticsearch.internal.utils.ElasticsearchUtils;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 *
 */
public class DocumentOperations extends ElasticsearchOperations {

    /**
     * Logging object
     */
    private static final Logger logger = Logger.getLogger(DocumentOperations.class.getName());

    /**
     * Index Document operation adds or updates a typed JSON document in a specific index, making it searchable.
     * 
     * @param esConnection
     *            The Elasticsearch connection
     * @param index
     *            Name of the index
     * @param documentId
     *            ID of the document
     * @param inputSource
     *            Get the JSON input file path or index mapping.
     * @param routing
     *            Routing is used to determine in which shard the document will reside in
     * @param timeoutInSec
     *            Timeout in seconds to wait for primary shard to become available
     * @param refreshPolicy
     *            Refresh policy is used to control when changes made by the requests are made visible to search. Option for refresh policy A) true : Refresh the relevant primary
     *            and replica shards (not the whole index) immediately after the operation occurs, so that the updated document appears in search results immediately. B) wait_for :
     *            Wait for the changes made by the request to be made visible by a refresh before replying. This doesn�t force an immediate refresh, rather, it waits for a
     *            refresh to happen. C) false (default) : Take no refresh related actions. The changes made by this request will be made visible at some point after the request
     *            returns.
     * @param version
     *            Version number of the indexed document. It will control the version of the document the operation is intended to be executed against.
     * @param versionType
     *            Version type: internal, external, external_gte
     * @param operationType
     *            Type of the operation. When create type is used, the index operation will fail if a document by that id already exists in the index.
     * @param pipeline
     *            Name of the ingest pipeline to be executed before indexing the document
     * @param callback
     */
    @MediaType(value = ANY, strict = false)
    @DisplayName("Document - Index")
    public void indexDocument(@Connection ElasticsearchConnection esConnection, @Placement(order = 1) @DisplayName("Index") String index,
            @Placement(order = 2) @DisplayName("Document Id") String documentId, @Placement(order = 3) @ParameterGroup(name = "Input Document") IndexDocumentOptions inputSource,
            @Placement(tab = "Optional Arguments", order = 1) @DisplayName("Routing") @Optional String routing,
            @Placement(tab = "Optional Arguments", order = 3) @DisplayName("Timeout (Seconds)") @Optional(defaultValue = "0") @Summary("Timeout in seconds to wait for primary shard") long timeoutInSec,
            @Placement(tab = "Optional Arguments", order = 4) @DisplayName("Refresh policy") @Optional RefreshPolicy refreshPolicy,
            @Placement(tab = "Optional Arguments", order = 5) @DisplayName("Version") @Optional long version,
            @Placement(tab = "Optional Arguments", order = 6) @DisplayName("Version Type") @Optional VersionType versionType,
            @Placement(tab = "Optional Arguments", order = 7) @DisplayName("Operation type") @Optional OpType operationType,
            @Placement(tab = "Optional Arguments", order = 8) @DisplayName("Pipeline") @Optional @Summary("The name of the ingest pipeline to be executed before indexing the document") String pipeline,
            CompletionCallback<IndexResponse, Void> callback) {

        IndexRequest indexRequest;
        try {
            if (inputSource.getJsonInputPath() != null) {
                indexRequest = new IndexRequest(index).id(documentId).source(ElasticsearchUtils.readFileToString(inputSource.getJsonInputPath()), XContentType.JSON);
            } else {
                indexRequest = new IndexRequest(index).id(documentId).source(inputSource.getDocumentSource());
            }

            if (timeoutInSec != 0) {
                indexRequest.timeout(TimeValue.timeValueSeconds(timeoutInSec));
            }

            ifPresent(routing, routingValue -> indexRequest.routing(routingValue));
            ifPresent(refreshPolicy, refreshPolicyValue -> indexRequest.setRefreshPolicy(refreshPolicyValue));
            if(version != 0) {
                indexRequest.version(version);
            }
            ifPresent(versionType, versionTypeValue -> indexRequest.versionType(versionTypeValue));
            ifPresent(operationType, operationTypeValue -> indexRequest.opType(operationTypeValue));
            ifPresent(pipeline, pipelineValue -> indexRequest.setPipeline(pipelineValue));

            IndexResponse indexResp = esConnection.getElasticsearchConnection().index(indexRequest, ElasticsearchUtils.getContentTypeJsonRequestOption());

            logger.info("Index Document operation Status : " + indexResp.status());
            responseConsumer(indexResp, callback);
        } catch (IOException e) {
            logger.error(e);
            throw new ElasticsearchException(ElasticsearchErrorTypes.OPERATION_FAILED, e);
        } catch (Exception e) {
            logger.error(e);
            throw new ElasticsearchException(ElasticsearchErrorTypes.EXECUTION, e);
        }
    }

    /**
     * Get Document operation allows to get a typed JSON document from the index based on its id.
     * 
     * @param esConnection
     *            The Elasticsearch connection
     * @param index
     *            Name of the index
     * @param documentId
     *            ID of the document
     * @param fetchSourceContext
     *            Enable or disable source retrieval
     * @param realtime
     *            Set realtime flag
     * @param routing
     *            Routing is used to determine in which shard the document will reside in
     * @param preference
     *            Preference value
     * @param refresh
     *            Perform a refresh before retrieving the document
     * @param version
     *            Version number of the indexed document
     * @param versionType
     *            Version type: internal, external, external_gte,
     * @param callback
     */
    @MediaType(value = ANY, strict = false)
    @DisplayName("Document - Get")
    public void getDocument(@Connection ElasticsearchConnection esConnection, @Placement(order = 1) @DisplayName("Index") String index,
            @Placement(order = 2) @DisplayName("Document Id") String documentId,
            @Placement(tab = "Optional Arguments", order = 1) @DisplayName("Source retrieval") @Optional DocumentFetchSourceOptions fetchSourceContext,
            @Placement(tab = "Optional Arguments", order = 2) @DisplayName("Routing") @Optional String routing,
            @Placement(tab = "Optional Arguments", order = 4) @DisplayName("Preference value") @Optional String preference,
            @Placement(tab = "Optional Arguments", order = 5) @DisplayName("Set realtime flag") @Optional(defaultValue = "true") boolean realtime,
            @Placement(tab = "Optional Arguments", order = 6) @DisplayName("Refresh") @Summary("Perform a refresh before retrieving the document") @Optional(defaultValue = "false") boolean refresh,
            @Placement(tab = "Optional Arguments", order = 7) @DisplayName("Version") @Optional long version,
            @Placement(tab = "Optional Arguments", order = 8) @DisplayName("Version Type") @Optional VersionType versionType, CompletionCallback<String, Void> callback) {

        GetRequest getRequest = new GetRequest(index, documentId);

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
        } else {
            getRequest.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);
        }

        ifPresent(routing, routingValue -> getRequest.routing(routingValue));
        ifPresent(preference, preferenceValue -> getRequest.preference(preferenceValue));
        if(version != 0) {
            getRequest.version(version);
        }
        ifPresent(versionType, versionTypeValue -> getRequest.versionType(versionTypeValue));

        getRequest.realtime(realtime);
        getRequest.refresh(refresh);
        GetResponse getResp;
        try {
            getResp = esConnection.getElasticsearchConnection().get(getRequest, ElasticsearchUtils.getContentTypeJsonRequestOption());
            logger.info("Get Response : " + getResp);
            responseConsumer(getResp.toString(), callback);
        } catch (IOException e) {
            logger.error(e);
            throw new ElasticsearchException(ElasticsearchErrorTypes.OPERATION_FAILED, e);
        } catch (Exception e) {
            logger.error(e);
            throw new ElasticsearchException(ElasticsearchErrorTypes.EXECUTION, e);
        }
    }

    /**
     * Delete Document operation allows to delete a typed JSON document from a specific index based on its id
     * 
     * @param esConnection
     *            The Elasticsearch connection
     * @param index
     *            Name of the index
     * @param documentId
     *            ID of the document
     * @param routing
     *            Routing is used to determine in which shard the document will reside in
     * @param timeoutInSec
     *            Time in seconds to wait for primary shard to become available
     * @param refreshPolicy
     *            Refresh policy is used to control when changes made by the requests are made visible to search. Option for refresh policy A) true : Refresh the relevant primary
     *            and replica shards (not the whole index) immediately after the operation occurs, so that the updated document appears in search results immediately. B) wait_for :
     *            Wait for the changes made by the request to be made visible by a refresh before replying. This doesn�t force an immediate refresh, rather, it waits for a
     *            refresh to happen. C) false (default) : Take no refresh related actions. The changes made by this request will be made visible at some point after the request
     *            returns.
     * @param version
     *            Version number of the indexed document
     * @param versionType
     *            Version type: internal, external, external_gte
     * @param callback
     */
    @MediaType(value = ANY, strict = false)
    @DisplayName("Document - Delete")
    public void deleteDocument(@Connection ElasticsearchConnection esConnection, @Placement(order = 1) @DisplayName("Index") String index,
            @Placement(order = 2) @DisplayName("Document Id") String documentId,
            @Placement(tab = "Optional Arguments", order = 1) @DisplayName("Routing value") @Optional String routing,
            @Placement(tab = "Optional Arguments", order = 3) @DisplayName("Timeout (Seconds)") @Optional(defaultValue = "0") @Summary("Timeout in seconds to wait for primary shard") long timeoutInSec,
            @Placement(tab = "Optional Arguments", order = 4) @DisplayName("Refresh policy") @Optional RefreshPolicy refreshPolicy,
            @Placement(tab = "Optional Arguments", order = 5) @DisplayName("Version") @Optional long version,
            @Placement(tab = "Optional Arguments", order = 6) @DisplayName("Version Type") @Optional VersionType versionType, CompletionCallback<DeleteResponse, Void> callback) {

        DeleteRequest deleteRequest = new DeleteRequest(index, documentId);

        if (timeoutInSec != 0) {
            deleteRequest.timeout(TimeValue.timeValueSeconds(timeoutInSec));
        }
        if (version != 0) {
            deleteRequest.version(version);
        }

        ifPresent(routing, routingValue -> deleteRequest.routing(routingValue));
        ifPresent(refreshPolicy, refreshPolicyValue -> deleteRequest.setRefreshPolicy(refreshPolicyValue));
        ifPresent(versionType, versionTypeValue -> deleteRequest.versionType(versionTypeValue));

        DeleteResponse deleteResp;
        try {
            deleteResp = esConnection.getElasticsearchConnection().delete(deleteRequest, ElasticsearchUtils.getContentTypeJsonRequestOption());
            logger.info("Delete document response : " + deleteResp);
            responseConsumer(deleteResp, callback);
        } catch (IOException e) {
            logger.error(e);
            throw new ElasticsearchException(ElasticsearchErrorTypes.OPERATION_FAILED, e);
        } catch (Exception e) {
            logger.error(e);
            throw new ElasticsearchException(ElasticsearchErrorTypes.EXECUTION, e);
        }
    }

    /**
     * Update Document operation allows to update a document based on a script provided.
     * 
     * @param esConnection
     *            The Elasticsearch connection
     * @param index
     *            Name of the index
     * @param documentId
     *            ID of the document
     * @param routing
     *            Routing is used to determine in which shard the document will reside in
     * @param inputSource
     *            Input document source
     * @param timeoutInSec
     *            Time in seconds to wait for primary shard to become available
     * @param refreshPolicy
     *            Refresh policy is used to control when changes made by the requests are made visible to search. Option for refresh policy A) true : Refresh the relevant primary
     *            and replica shards (not the whole index) immediately after the operation occurs, so that the updated document appears in search results immediately. B) wait_for :
     *            Wait for the changes made by the request to be made visible by a refresh before replying. This doesn�t force an immediate refresh, rather, it waits for a
     *            refresh to happen. C) false (default) : Take no refresh related actions. The changes made by this request will be made visible at some point after the request
     *            returns.
     * @param retryOnConflict
     *            How many times to retry the update operation if the document to update has been changed by another operation between the get and indexing phases of the update
     *            operation
     * @param fetchSource
     *            Enable or disable source retrieval
     * @param ifSeqNo        
     *            If set, only perform this update request if the document was last modification was assigned this sequence number.
     * @param ifPrimaryTerm           
     *            If set, only perform this update request if the document was last modification was assigned this primary term.
     * @param version
     *            Version number of the indexed document
     * @param detectNoop
     *            Enable or disable the noop detection
     * @param scriptedUpsert
     *            Indicate that the script must run regardless of whether the document exists or not
     * @param docAsUpsert
     *            Indicate that the partial document must be used as the upsert document if it does not exist yet.
     * @param waitForActiveShards
     *            The number of shard copies that must be active before proceeding with the update operation.
     * @param callback
     */
    @MediaType(value = ANY, strict = false)
    @DisplayName("Document - Update")
    public void updateDocument(@Connection ElasticsearchConnection esConnection, @Placement(order = 1) @DisplayName("Index") String index,
            @Placement(order = 2) @DisplayName("Document Id") String documentId, @Placement(order = 3) @ParameterGroup(name = "Input Document") IndexDocumentOptions inputSource,
            @Placement(tab = "Optional Arguments", order = 1) @DisplayName("Routing") @Optional String routing,
            @Placement(tab = "Optional Arguments", order = 3) @DisplayName("Timeout (Seconds)") @Optional(defaultValue = "0") @Summary("Timeout in seconds to wait for primary shard") long timeoutInSec,
            @Placement(tab = "Optional Arguments", order = 4) @DisplayName("Refresh policy") @Optional RefreshPolicy refreshPolicy,
            @Placement(tab = "Optional Arguments", order = 5) @DisplayName("Retry on Conflict") @Optional(defaultValue = "0") @Summary("How many times to retry the update operation if the document to update has been changed by another operation between the get and indexing phases of the update operation") int retryOnConflict,
            @Placement(tab = "Optional Arguments", order = 6) @DisplayName("Fetch Source") @Optional(defaultValue = "false") boolean fetchSource,
            @Placement(tab = "Optional Arguments", order = 7) @DisplayName("If Seq No") @Optional long ifSeqNo,
            @Placement(tab = "Optional Arguments", order = 7) @DisplayName("If Primary Term") @Optional long ifPrimaryTerm,
            @Placement(tab = "Optional Arguments", order = 8) @DisplayName("Noop Detection") @Optional(defaultValue = "true") boolean detectNoop,
            @Placement(tab = "Optional Arguments", order = 9) @DisplayName("Scripted Upsert") @Optional(defaultValue = "false") boolean scriptedUpsert,
            @Placement(tab = "Optional Arguments", order = 10) @DisplayName("Doc Upsert") @Optional(defaultValue = "false") boolean docAsUpsert,
            @Placement(tab = "Optional Arguments", order = 9) @DisplayName("Wait for Active Shards") @Optional int waitForActiveShards,
            CompletionCallback<UpdateResponse, Void> callback) {
        try {
            UpdateRequest updateRequest = new UpdateRequest(index, documentId);
            if (inputSource.getJsonInputPath() != null) {
                updateRequest.doc(ElasticsearchUtils.readFileToString(inputSource.getJsonInputPath()), XContentType.JSON);
            } else {
                updateRequest.doc(inputSource.getDocumentSource());
            }
            
            ifPresent(routing, routingValue -> updateRequest.routing(routingValue));
            if (timeoutInSec != 0) {
                updateRequest.timeout(TimeValue.timeValueSeconds(timeoutInSec));
            }
            ifPresent(refreshPolicy, refreshPolicyValue -> updateRequest.setRefreshPolicy(refreshPolicyValue));
            if (retryOnConflict != 0) {
                updateRequest.retryOnConflict(retryOnConflict);
            }
            updateRequest.fetchSource(fetchSource);
            if (ifSeqNo != 0) {
                updateRequest.setIfSeqNo(ifSeqNo);
            }
            if (ifPrimaryTerm != 0) {
                updateRequest.setIfPrimaryTerm(ifPrimaryTerm); 
            }
            updateRequest.detectNoop(detectNoop);
            updateRequest.scriptedUpsert(scriptedUpsert);
            updateRequest.docAsUpsert(docAsUpsert);
            if (waitForActiveShards != 0) {
                updateRequest.waitForActiveShards(waitForActiveShards); 
            }
            
            UpdateResponse updateResp = esConnection.getElasticsearchConnection().update(updateRequest, ElasticsearchUtils.getContentTypeJsonRequestOption());
            logger.info("Update Response : " + updateResp);
            responseConsumer(updateResp, callback);
        } catch (Exception e) {
            throw new ElasticsearchException(ElasticsearchErrorTypes.OPERATION_FAILED, e);
        }
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
    @DisplayName("Document - Bulk")
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

            Request request = new Request("POST", resource);
            request.addParameters(params);
            request.setEntity(entity);
            Response response = esConnection.getElasticsearchConnection().getLowLevelClient().performRequest(request);
            logger.info("Bulk operation response : " + response);
            return response;
        } catch (Exception e) {
            throw new ElasticsearchException(ElasticsearchErrorTypes.OPERATION_FAILED, e);
        }
    }
}