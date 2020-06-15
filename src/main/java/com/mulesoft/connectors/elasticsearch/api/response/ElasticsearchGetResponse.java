package com.mulesoft.connectors.elasticsearch.api.response;

import org.elasticsearch.action.get.GetResponse;

/**
 * The response of a get action.
 */
public class ElasticsearchGetResponse {

    /**
     * The id of the document.
     */
    private String id;

    /**
     * The index the document was fetched from.
     */
    private String index;

    /**
     * The primary term of the last primary that has changed this document, if found.
     */
    private long primaryTerm;

    /**
     * The sequence number assigned to the last operation that has changed this document, if found.
     */
    private long seqNo;

    /**
     * The source of the document (as a string).
     */
    private String sourceAsString;

    /**
     * The type of the document.
     */
    private String type;

    /**
     * The version of the doc.
     */
    private long version;

    /**
     * Does the document exists.
     */
    private boolean found;

    public ElasticsearchGetResponse() {
        // Default constructor is to make the object DataWeave compatible.
    }

    public ElasticsearchGetResponse(GetResponse getResponse) {
        this.id = getResponse.getId();
        this.index = getResponse.getIndex();
        this.primaryTerm = getResponse.getPrimaryTerm();
        this.seqNo = getResponse.getSeqNo();
        this.sourceAsString = getResponse.getSourceAsString();
        this.type = getResponse.getType();
        this.version = getResponse.getVersion();
        this.found = getResponse.isExists();
    }

    public String getId() {
        return id;
    }

    public String getIndex() {
        return index;
    }

    public long getPrimaryTerm() {
        return primaryTerm;
    }

    public long getSeqNo() {
        return seqNo;
    }

    public String getSourceAsString() {
        return sourceAsString;
    }

    public String getType() {
        return type;
    }

    public long getVersion() {
        return version;
    }

    public boolean isFound() {
        return found;
    }

    @Override
    public String toString() {
        return "ElasticsearchGetResponse [id=" + id + ", index=" + index + ", primaryTerm=" + primaryTerm + ", seqNo=" + seqNo + ", sourceAsString=" + sourceAsString + ", type="
                + type + ", version=" + version + ", found=" + found + "]";
    }

}
