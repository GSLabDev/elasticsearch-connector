/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.extension.elastic.search;

import static org.junit.Assert.assertNotNull;

import org.elasticsearch.action.search.ClearScrollResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.functional.junit4.MuleArtifactFunctionalTestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchOperationTest extends MuleArtifactFunctionalTestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchOperationTest.class);

    @Override
    protected String getConfigFile() {
        return "search-operation-test.xml";
    }

    @Before
    public void setUp() throws Exception {
        flowRunner("setUpFlow").run();
    }

    @Test
    public void executeMatchAllSearchOperation() throws Exception {

        SearchResponse payloadValue = ((SearchResponse) flowRunner("testMatchAllSearchFlow").run().getMessage().getPayload().getValue());
        assertNotNull(payloadValue);
    }

    @Test
    public void executeSearchMatchOperation() throws Exception {
        SearchResponse payloadValue = ((SearchResponse) flowRunner("testSearchMatchFlow").run().getMessage().getPayload().getValue());
        LOGGER.info(payloadValue.toString());

        assertNotNull(payloadValue);
    }

    @Test
    public void executeMultiMatchOperation() throws Exception {
        SearchResponse payloadValue = ((SearchResponse) flowRunner("testMultiMatchFlow").run().getMessage().getPayload().getValue());
        LOGGER.info(payloadValue.toString());

        assertNotNull(payloadValue);
    }

    @Test
    public void executSearchMatchPhraseOperation() throws Exception {
        SearchResponse payloadValue = ((SearchResponse) flowRunner("testSearchMatchPhraseFlow").run().getMessage().getPayload().getValue());
        LOGGER.info(payloadValue.toString());

        assertNotNull(payloadValue);
    }

    @Test
    public void executSearchMatchPrefixOperation() throws Exception {
        SearchResponse payloadValue = ((SearchResponse) flowRunner("testSearchMatchPrefixFlow").run().getMessage().getPayload().getValue());
        LOGGER.info(payloadValue.toString());

        assertNotNull(payloadValue);
    }

    @Test
    public void executSearchCommomTermsOperation() throws Exception {
        SearchResponse payloadValue = ((SearchResponse) flowRunner("testSearchCommomTermsFlow").run().getMessage().getPayload().getValue());
        LOGGER.info(payloadValue.toString());

        assertNotNull(payloadValue);
    }

    @Test
    public void executSearchQueryStringOperation() throws Exception {
        SearchResponse payloadValue = ((SearchResponse) flowRunner("testSearchQueryStringFlow").run().getMessage().getPayload().getValue());
        LOGGER.info(payloadValue.toString());

        assertNotNull(payloadValue);
    }

    @Test
    public void executSearchSimpleQueryStringOperation() throws Exception {
        SearchResponse payloadValue = ((SearchResponse) flowRunner("testSearchSimpleQueryStringFlow").run().getMessage().getPayload().getValue());
        LOGGER.info(payloadValue.toString());

        assertNotNull(payloadValue);
    }

    @Test
    public void executSearchUsingJsonRequestOperation() throws Exception {
        String payloadValue = ((String) flowRunner("testSearchUsingJsonRequestFlow").run().getMessage().getPayload().getValue());
        LOGGER.info(payloadValue.toString());

        assertNotNull(payloadValue);
    }

    @Test
    public void executSearchScrollInitOperation() throws Exception {
        SearchResponse payloadValue = ((SearchResponse) flowRunner("testSearchScrollInitFlow").run().getMessage().getPayload().getValue());
        LOGGER.info(payloadValue.toString());
        assertNotNull(payloadValue);
        assertNotNull(payloadValue.getScrollId());
    }

    @Test
    public void executSearchScrollOperation() throws Exception {
        SearchResponse payloadValue = ((SearchResponse) flowRunner("testSearchScrollInitFlow").run().getMessage().getPayload().getValue());
        LOGGER.info(payloadValue.toString());
        String key = "scrollId";
        String value = payloadValue.getScrollId();

        payloadValue = (SearchResponse) flowRunner("testSearchScrollFlow").withVariable(key, value).run().getMessage().getPayload().getValue();
        LOGGER.info(payloadValue.toString());
        assertNotNull(payloadValue);
        assertNotNull(value);
        value = payloadValue.getScrollId();
    }

    @Test
    public void executClearScrollOperation() throws Exception {
        SearchResponse payloadValue = ((SearchResponse) flowRunner("testSearchScrollInitFlow").run().getMessage().getPayload().getValue());
        LOGGER.info(payloadValue.toString());
        String key = "scrollId";
        String value = payloadValue.getScrollId();

        ClearScrollResponse response = (ClearScrollResponse) flowRunner("testClearScrollFlow").withVariable(key, value).run().getMessage().getPayload().getValue();

        LOGGER.info(response.toString());
        assertNotNull(response);

    }

    @Test
    public void executSearchWithoutIndexOperation() throws Exception {
        SearchResponse payloadValue = (SearchResponse) flowRunner("testSearchWithoutIndexFlow").run().getMessage().getPayload().getValue();
        LOGGER.info(payloadValue.toString());
        assertNotNull(payloadValue);
    }

    @Test
    public void executSearchCommomTermsWithDefaultOperation() throws Exception {
        SearchResponse payloadValue = (SearchResponse) flowRunner("SearchCommomTermsWithDefaultFlow").run().getMessage().getPayload().getValue();
        LOGGER.info(payloadValue.toString());
        assertNotNull(payloadValue);
    }

    @Test
    public void executSearchMatchTermsWithDefaultOperation() throws Exception {
        SearchResponse payloadValue = (SearchResponse) flowRunner("testSearchMatchWithDefaultFlow").run().getMessage().getPayload().getValue();
        LOGGER.info(payloadValue.toString());
        assertNotNull(payloadValue);
    }

    @Test
    public void executSearchMatchPhraseWithDefaultOperation() throws Exception {
        SearchResponse payloadValue = (SearchResponse) flowRunner("testSearchMatchPhraseWithDefaultFlow").run().getMessage().getPayload().getValue();
        LOGGER.info(payloadValue.toString());
        assertNotNull(payloadValue);
    }

    @Test
    public void executMultiMatchWithDefaultOperation() throws Exception {
        SearchResponse payloadValue = (SearchResponse) flowRunner("testMultiMatchWithDefaultFlow").run().getMessage().getPayload().getValue();
        LOGGER.info(payloadValue.toString());
        assertNotNull(payloadValue);
    }

    @Test
    public void executSearchSimpleQueryStringWithDefaultOperation() throws Exception {
        SearchResponse payloadValue = (SearchResponse) flowRunner("testSearchSimpleQueryStringWithDefaultFlow").run().getMessage().getPayload().getValue();
        LOGGER.info(payloadValue.toString());
        assertNotNull(payloadValue);
    }

    @Test
    public void executtestSearchQueryStringWithDefaultOperation() throws Exception {
        SearchResponse payloadValue = (SearchResponse) flowRunner("testSearchQueryStringWithDefaultFlow").run().getMessage().getPayload().getValue();
        LOGGER.info(payloadValue.toString());
        assertNotNull(payloadValue);
    }

    @After
    public void tearDown() throws Exception {
        flowRunner("tearDownFlow").run();
    }
}
