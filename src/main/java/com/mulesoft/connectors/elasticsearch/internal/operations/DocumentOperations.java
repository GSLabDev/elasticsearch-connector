/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.internal.operations;

import static com.mulesoft.connectors.elasticsearch.internal.utils.ElasticsearchUtils.ifPresent;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Request;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.mule.runtime.extension.api.annotation.metadata.OutputResolver;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.apache.log4j.Logger;

import com.mulesoft.connectors.elasticsearch.api.DocumentFetchSourceOptions;
import com.mulesoft.connectors.elasticsearch.api.ElasticsearchRefreshPolicy;
import com.mulesoft.connectors.elasticsearch.api.ElasticsearchVersionType;
import com.mulesoft.connectors.elasticsearch.api.OperationType;
import com.mulesoft.connectors.elasticsearch.api.document.DeleteDocumentConfiguration;
import com.mulesoft.connectors.elasticsearch.api.document.IndexDocumentOptions;
import com.mulesoft.connectors.elasticsearch.api.JsonData;
import com.mulesoft.connectors.elasticsearch.api.response.ElasticsearchGetResponse;
import com.mulesoft.connectors.elasticsearch.api.response.ElasticsearchResponse;
import com.mulesoft.connectors.elasticsearch.internal.connection.ElasticsearchConnection;
import com.mulesoft.connectors.elasticsearch.internal.error.ElasticsearchErrorTypes;
import com.mulesoft.connectors.elasticsearch.internal.error.exception.ElasticsearchException;
import com.mulesoft.connectors.elasticsearch.internal.metadata.DeleteResponseOutputMetadataResolver;
import com.mulesoft.connectors.elasticsearch.internal.metadata.GetResponseOutputMetadataResolver;
import com.mulesoft.connectors.elasticsearch.internal.metadata.IndexResponseOutputMetadataResolver;
import com.mulesoft.connectors.elasticsearch.internal.metadata.ResponseOutputMetadataResolver;
import com.mulesoft.connectors.elasticsearch.internal.metadata.UpdateResponseOutputMetadataResolver;
import com.mulesoft.connectors.elasticsearch.internal.utils.ElasticsearchDocumentUtils;
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
     * @return IndexResponse as JSON String
     */
    @MediaType(MediaType.APPLICATION_JSON)
    @DisplayName("Document - Index")
    @OutputResolver(output = IndexResponseOutputMetadataResolver.class)
    public String indexDocument(@Connection ElasticsearchConnection esConnection, @Placement(order = 1) @DisplayName("Index") String index,
            @Placement(order = 2) @DisplayName("Document Id") String documentId, @Placement(order = 3) @ParameterGroup(name = "Input Document") IndexDocumentOptions inputSource,
            @Placement(tab = "Optional Arguments", order = 1) @DisplayName("Routing") @Optional String routing,
            @Placement(tab = "Optional Arguments", order = 3) @DisplayName("Timeout (Seconds)") @Optional(defaultValue = "0") @Summary("Timeout in seconds to wait for primary shard") long timeoutInSec,
            @Placement(tab = "Optional Arguments", order = 4) @DisplayName("Refresh policy") @Optional ElasticsearchRefreshPolicy refreshPolicy,
            @Placement(tab = "Optional Arguments", order = 5) @DisplayName("Version") @Optional long version,
            @Placement(tab = "Optional Arguments", order = 6) @DisplayName("Version Type") @Optional ElasticsearchVersionType versionType,
            @Placement(tab = "Optional Arguments", order = 7) @DisplayName("Operation type") @Optional OperationType operationType,
            @Placement(tab = "Optional Arguments", order = 8) @DisplayName("Pipeline") @Optional @Summary("The name of the ingest pipeline to be executed before indexing the document") String pipeline) {

        IndexRequest indexRequest;
        String response = null;
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
            ifPresent(refreshPolicy, refreshPolicyValue -> indexRequest.setRefreshPolicy(refreshPolicyValue.getRefreshPolicy()));
            if(version != 0) {
                indexRequest.version(version);
            }
            ifPresent(versionType, versionTypeValue -> indexRequest.versionType(versionTypeValue.getVersionType()));
            ifPresent(operationType, operationTypeValue -> indexRequest.opType(operationTypeValue.getOpType()));
            ifPresent(pipeline, pipelineValue -> indexRequest.setPipeline(pipelineValue));

            IndexResponse indexResp = esConnection.getElasticsearchConnection().index(indexRequest, ElasticsearchUtils.getContentTypeJsonRequestOption());

            logger.info("Index Document operation Status : " + indexResp.status());
            response = getJsonResponse(indexResp);
        } catch (IOException e) {
            logger.error(e);
            throw new ElasticsearchException(ElasticsearchErrorTypes.OPERATION_FAILED, e);
        } catch (Exception e) {
            logger.error(e);
            throw new ElasticsearchException(ElasticsearchErrorTypes.EXECUTION, e);
        }
        return response;
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
     * @return GetResponse as JSON String
     */
    @MediaType(MediaType.APPLICATION_JSON)
    @DisplayName("Document - Get")
    @OutputResolver(output = GetResponseOutputMetadataResolver.class)
    public String getDocument(@Connection ElasticsearchConnection esConnection, @Placement(order = 1) @DisplayName("Index") String index,
            @Placement(order = 2) @DisplayName("Document Id") String documentId,
            @Placement(tab = "Optional Arguments", order = 1) @DisplayName("Source retrieval") @Optional DocumentFetchSourceOptions fetchSourceContext,
            @Placement(tab = "Optional Arguments", order = 2) @DisplayName("Routing") @Optional String routing,
            @Placement(tab = "Optional Arguments", order = 4) @DisplayName("Preference value") @Optional String preference,
            @Placement(tab = "Optional Arguments", order = 5) @DisplayName("Set realtime flag") @Optional(defaultValue = "true") boolean realtime,
            @Placement(tab = "Optional Arguments", order = 6) @DisplayName("Refresh") @Summary("Perform a refresh before retrieving the document") @Optional(defaultValue = "false") boolean refresh,
            @Placement(tab = "Optional Arguments", order = 7) @DisplayName("Version") @Optional long version,
            @Placement(tab = "Optional Arguments", order = 8) @DisplayName("Version Type") @Optional ElasticsearchVersionType versionType) {

        GetRequest getRequest = new GetRequest(index, documentId);
        String response = null;

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
        ifPresent(versionType, versionTypeValue -> getRequest.versionType(versionTypeValue.getVersionType()));

        getRequest.realtime(realtime);
        getRequest.refresh(refresh);
        try {
            ElasticsearchGetResponse getResponse = new ElasticsearchGetResponse(esConnection.getElasticsearchConnection().get(getRequest, ElasticsearchUtils.getContentTypeJsonRequestOption()));
            logger.info("Get Response : " + getResponse);
            response = getJsonResponse(getResponse);
        } catch (IOException e) {
            logger.error(e);
            throw new ElasticsearchException(ElasticsearchErrorTypes.OPERATION_FAILED, e);
        } catch (Exception e) {
            logger.error(e);
            throw new ElasticsearchException(ElasticsearchErrorTypes.EXECUTION, e);
        }
        return response;
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
     * @param deleteDocumentConfiguration
     *            Delete document configuration
     * @return DeleteResponse as JSON String
     */
    @MediaType(MediaType.APPLICATION_JSON)
    @DisplayName("Document - Delete")
    @OutputResolver(output = DeleteResponseOutputMetadataResolver.class)
    public String deleteDocument(@Connection ElasticsearchConnection esConnection, @Placement(order = 1) @DisplayName("Index") String index,
            @Placement(order = 2) @DisplayName("Document Id") String documentId,
            @Placement(tab = "Optional Arguments", order = 1) @Optional DeleteDocumentConfiguration deleteDocumentConfiguration ) {
        String response = null;
        DeleteRequest deleteRequest = new DeleteRequest(index, documentId);

        DeleteResponse deleteResp;
        try {
            if(deleteDocumentConfiguration != null) {
                ElasticsearchDocumentUtils.configureDeleteDocumentReq(deleteRequest, deleteDocumentConfiguration);
            }
            
            deleteResp = esConnection.getElasticsearchConnection().delete(deleteRequest, ElasticsearchUtils.getContentTypeJsonRequestOption());
            logger.info("Delete document response : " + deleteResp);
            response = getJsonResponse(deleteResp);
        } catch (IOException e) {
            logger.error(e);
            throw new ElasticsearchException(ElasticsearchErrorTypes.OPERATION_FAILED, e);
        } catch (Exception e) {
            logger.error(e);
            throw new ElasticsearchException(ElasticsearchErrorTypes.EXECUTION, e);
        }
        return response;
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
     * @param detectNoop
     *            Enable or disable the noop detection
     * @param scriptedUpsert
     *            Indicate that the script must run regardless of whether the document exists or not
     * @param docAsUpsert
     *            Indicate that the partial document must be used as the upsert document if it does not exist yet.
     * @param waitForActiveShards
     *            The number of shard copies that must be active before proceeding with the update operation.
     * @return UpdateResponse as JSON String
     */
    @MediaType(MediaType.APPLICATION_JSON)
    @DisplayName("Document - Update")
    @OutputResolver(output = UpdateResponseOutputMetadataResolver.class)
    public String updateDocument(@Connection ElasticsearchConnection esConnection, @Placement(order = 1) @DisplayName("Index") String index,
            @Placement(order = 2) @DisplayName("Document Id") String documentId, @Placement(order = 3) @ParameterGroup(name = "Input Document") IndexDocumentOptions inputSource,
            @Placement(tab = "Optional Arguments", order = 1) @DisplayName("Routing") @Optional String routing,
            @Placement(tab = "Optional Arguments", order = 3) @DisplayName("Timeout (Seconds)") @Optional(defaultValue = "0") @Summary("Timeout in seconds to wait for primary shard") long timeoutInSec,
            @Placement(tab = "Optional Arguments", order = 4) @DisplayName("Refresh policy") @Optional ElasticsearchRefreshPolicy refreshPolicy,
            @Placement(tab = "Optional Arguments", order = 5) @DisplayName("Retry on Conflict") @Optional(defaultValue = "0") @Summary("How many times to retry the update operation if the document to update has been changed by another operation between the get and indexing phases of the update operation") int retryOnConflict,
            @Placement(tab = "Optional Arguments", order = 6) @DisplayName("Fetch Source") @Optional(defaultValue = "false") boolean fetchSource,
            @Placement(tab = "Optional Arguments", order = 7) @DisplayName("If Seq No") @Optional long ifSeqNo,
            @Placement(tab = "Optional Arguments", order = 7) @DisplayName("If Primary Term") @Optional long ifPrimaryTerm,
            @Placement(tab = "Optional Arguments", order = 8) @DisplayName("Noop Detection") @Optional(defaultValue = "true") boolean detectNoop,
            @Placement(tab = "Optional Arguments", order = 9) @DisplayName("Scripted Upsert") @Optional(defaultValue = "false") boolean scriptedUpsert,
            @Placement(tab = "Optional Arguments", order = 10) @DisplayName("Doc Upsert") @Optional(defaultValue = "false") boolean docAsUpsert,
            @Placement(tab = "Optional Arguments", order = 9) @DisplayName("Wait for Active Shards") @Optional int waitForActiveShards) {
        String response = null;
        
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
            ifPresent(refreshPolicy, refreshPolicyValue -> updateRequest.setRefreshPolicy(refreshPolicyValue.getRefreshPolicy()));
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
            response = getJsonResponse(updateResp);
        } catch (Exception e) {
            throw new ElasticsearchException(ElasticsearchErrorTypes.OPERATION_FAILED, e);
        }
        return response;
    }

    /**
     * Bulk operation makes it possible to perform many create, index, delete and update operations in a single API call.
     * 
     * @param esConnection
     *            The Elasticsearch connection
     * @param index
     *            Index name on which bulk operation performed.
     * @param jsonData
     *            Input file / data with list of operations to be performed like create, index, delete, update.
     * @return Response as JSON String
     */
    @MediaType(MediaType.APPLICATION_JSON)
    @DisplayName("Document - Bulk")
    @OutputResolver(output = ResponseOutputMetadataResolver.class)
    public String bulkOperation(@Connection ElasticsearchConnection esConnection, @Optional String index,
            @ParameterGroup(name = "Input data") JsonData jsonData) {
        String result = null;
        String resource = index != null ? "/" + index + "/_bulk" : "/_bulk";
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
            ElasticsearchResponse response = new ElasticsearchResponse(esConnection.getElasticsearchConnection().getLowLevelClient().performRequest(request));
            logger.info("Bulk operation response : " + response);
            result = getJsonResponse(response);
        } catch (Exception e) {
            throw new ElasticsearchException(ElasticsearchErrorTypes.OPERATION_FAILED, e);
        }
        return result;
    }
}