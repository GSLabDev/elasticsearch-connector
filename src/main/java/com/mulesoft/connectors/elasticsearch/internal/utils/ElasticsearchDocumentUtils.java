/**
 * Copyright (c) 2003-2020, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.2, a copy of which has been included with this distribution in the LICENSE.md file.
 */
/**
 *
 */
package com.mulesoft.connectors.elasticsearch.internal.utils;

import static com.mulesoft.connectors.elasticsearch.internal.utils.ElasticsearchUtils.ifPresent;

import java.io.IOException;
import org.apache.log4j.Logger;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import com.mulesoft.connectors.elasticsearch.api.DocumentFetchSourceOptions;
import com.mulesoft.connectors.elasticsearch.api.document.DocumentConfiguration;
import com.mulesoft.connectors.elasticsearch.api.document.GetDocumentConfiguration;
import com.mulesoft.connectors.elasticsearch.api.document.IndexDocumentConfiguration;
import com.mulesoft.connectors.elasticsearch.api.document.UpdateDocumentConfiguration;

public class ElasticsearchDocumentUtils {

    /**
     * Logging object
     */
    private static final Logger logger = Logger.getLogger(ElasticsearchDocumentUtils.class.getName());

    private ElasticsearchDocumentUtils() {
    }

    public static void configureDeleteDocumentReq(DeleteRequest deleteReq, DocumentConfiguration deleteDocumentConfiguration) throws IOException {

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

    public static void configureIndexReq(IndexRequest indexReq, IndexDocumentConfiguration indexDocumentConfiguration) throws IOException {

        if (indexDocumentConfiguration.getTimeoutInSec() != 0) {
            indexReq.timeout(TimeValue.timeValueSeconds(indexDocumentConfiguration.getTimeoutInSec()));
        }

        ifPresent(indexDocumentConfiguration.getRouting(), routingValue -> indexReq.routing(routingValue));
        ifPresent(indexDocumentConfiguration.getRefreshPolicy(), refreshPolicyValue -> indexReq.setRefreshPolicy(refreshPolicyValue.getRefreshPolicy()));

        if (indexDocumentConfiguration.getVersion() != 0) {
            indexReq.version(indexDocumentConfiguration.getVersion());
        }

        ifPresent(indexDocumentConfiguration.getVersionType(), versionTypeValue -> indexReq.versionType(versionTypeValue.getVersionType()));
        ifPresent(indexDocumentConfiguration.getOperationType(), operationTypeValue -> indexReq.opType(operationTypeValue.getOpType()));
        ifPresent(indexDocumentConfiguration.getPipeline(), pipelineValue -> indexReq.setPipeline(pipelineValue));

        logger.debug("Index request : " + indexReq);
    }

    public static void configureGetReq(GetRequest getReq, GetDocumentConfiguration getDocumentConfiguration) throws IOException {

        DocumentFetchSourceOptions fetchSourceContext = getDocumentConfiguration.getFetchSourceContext();
        if (fetchSourceContext != null && fetchSourceContext.isFetchSource()) {
            String[] includes = Strings.EMPTY_ARRAY, excludes = Strings.EMPTY_ARRAY;

            if (fetchSourceContext.getIncludeFields() != null) {
                includes = fetchSourceContext.getIncludeFields().toArray(new String[0]);
            }

            if (fetchSourceContext.getExcludeFields() != null) {
                excludes = fetchSourceContext.getExcludeFields().toArray(new String[0]);
            }

            FetchSourceContext fetchSource = new FetchSourceContext(true, includes, excludes);
            getReq.fetchSourceContext(fetchSource);
        } else {
            getReq.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);
        }

        ifPresent(getDocumentConfiguration.getRouting(), routingValue -> getReq.routing(routingValue));
        ifPresent(getDocumentConfiguration.getPreference(), preferenceValue -> getReq.preference(preferenceValue));
        if (getDocumentConfiguration.getVersion() != 0) {
            getReq.version(getDocumentConfiguration.getVersion());
        }
        ifPresent(getDocumentConfiguration.getVersionType(), versionTypeValue -> getReq.versionType(versionTypeValue.getVersionType()));

        getReq.realtime(getDocumentConfiguration.isRealtime());
        getReq.refresh(getDocumentConfiguration.isRefresh());

        logger.debug("Get request : " + getReq);
    }

    public static void configureUpdateReq(UpdateRequest updateReq, UpdateDocumentConfiguration updateDocumentConfiguration) throws IOException {

        ifPresent(updateDocumentConfiguration.getRouting(), routingValue -> updateReq.routing(routingValue));

        if (updateDocumentConfiguration.getTimeoutInSec() != 0) {
            updateReq.timeout(TimeValue.timeValueSeconds(updateDocumentConfiguration.getTimeoutInSec()));
        }

        ifPresent(updateDocumentConfiguration.getRefreshPolicy(), refreshPolicyValue -> updateReq.setRefreshPolicy(refreshPolicyValue.getRefreshPolicy()));

        if (updateDocumentConfiguration.getRetryOnConflict() != 0) {
            updateReq.retryOnConflict(updateDocumentConfiguration.getRetryOnConflict());
        }

        updateReq.fetchSource(updateDocumentConfiguration.isFetchSource());

        if (updateDocumentConfiguration.getIfSeqNo() != 0) {
            updateReq.setIfSeqNo(updateDocumentConfiguration.getIfSeqNo());
        }

        if (updateDocumentConfiguration.getIfPrimaryTerm() != 0) {
            updateReq.setIfPrimaryTerm(updateDocumentConfiguration.getIfPrimaryTerm());
        }

        updateReq.detectNoop(updateDocumentConfiguration.isDetectNoop());
        updateReq.scriptedUpsert(updateDocumentConfiguration.isScriptedUpsert());
        updateReq.docAsUpsert(updateDocumentConfiguration.isDocAsUpsert());

        if (updateDocumentConfiguration.getWaitForActiveShards() != 0) {
            updateReq.waitForActiveShards(updateDocumentConfiguration.getWaitForActiveShards());
        }

        logger.debug("Update request : " + updateReq);
    }
}
