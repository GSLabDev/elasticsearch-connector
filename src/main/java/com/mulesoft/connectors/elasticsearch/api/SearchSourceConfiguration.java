/**
 * Copyright (c) 2003-2020, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.2, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.api;

import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 * 
 *         Search source request configuration parameters
 */
public class SearchSourceConfiguration extends DocumentFetchSourceOptions{

    /**
     * Search sorting types
     */
    public enum SortOrder {
        ASC,
        DESC;
    }

    /**
     * Retrieve search result from a certain offset
     */
    @Parameter
    @Optional(defaultValue = "0")
    private int from;

    /**
     * The number of search hits to return
     */
    @Parameter
    @Optional(defaultValue = "10")
    private int size;

    /**
     * Time (in minutes) allowed to finish the search operation
     */
    @Parameter
    @Optional
    private int timeout;

    /**
     * The sort order of search result using default field (_score).
     */
    @Parameter
    @Optional
    private SortOrder sortOrder;

    /**
     * Field name to sort the search result
     */
    @Parameter
    @Optional
    private String sortByFieldName;

    /**
     * Enable the profiling to the execution of search queries
     */
    @Parameter
    @Optional
    private boolean profile;

    /**
     * Explain the execution of search queries
     */
    @Parameter
    @Optional
    private boolean explain;

    /**
     * The maximum number of documents to collect for each shard
     */
    @Parameter
    @Optional
    private int terminateAfter;

    /**
     * Enable the search source tracking
     */
    @Parameter
    @Optional(defaultValue = "false")
    private boolean trackScores;

    /**
     * Enable the total hits tracking i.e whether the total hit count for the query should be tracked.
     */
    @Parameter
    @Optional
    private boolean trackTotalHits;
    
    /**
     * The total hit count that should be tracked.
     */
    @Parameter
    @Optional
    private int trackTotalHitsUpTo;

    /**
     * Whether the document's version will be included in the search hits.
     */
    @Parameter
    @Optional
    private boolean version;
    
    /**
     * The minimum score below which docs will be filtered out.
     */
    @Parameter
    @Optional
    private float minimumScore;
    
    /**
     * Whether search hits be returned with the sequence number and primary term of the last modification of the document.
     */
    @Parameter
    @Optional
    private boolean seqNoAndPrimaryTerm;
    
    public SearchSourceConfiguration() {
        // This default constructor makes the class DataWeave compatible.
    }

    public int getFrom() {
        return from;
    }

    public int getSize() {
        return size;
    }

    public int getTimeout() {
        return timeout;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public String getSortByFieldName() {
        return sortByFieldName;
    }

    public boolean isProfile() {
        return profile;
    }

    public boolean isExplain() {
        return explain;
    }

    public int getTerminateAfter() {
        return terminateAfter;
    }

    public boolean isTrackScores() {
        return trackScores;
    }

    public boolean isTrackTotalHits() {
        return trackTotalHits;
    }    

    public int getTrackTotalHitsUpTo() {
        return trackTotalHitsUpTo;
    }

    public boolean isVersion() {
        return version;
    }
    
    public float getMinimumScore() {
        return minimumScore;
    }

    public boolean isSeqNoAndPrimaryTerm() {
        return seqNoAndPrimaryTerm;
    }
}
