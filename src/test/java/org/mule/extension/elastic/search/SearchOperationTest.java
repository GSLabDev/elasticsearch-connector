/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.extension.elastic.search;

import static org.junit.Assert.assertNotNull;

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
        String payloadValue = (String) flowRunner("testMatchAllSearchFlow").run().getMessage().getPayload().getValue();
        assertNotNull(payloadValue);
    }

    @Test
    public void executeSearchMatchOperation() throws Exception {
        String payloadValue = (String) flowRunner("testSearchMatchFlow").run().getMessage().getPayload().getValue();
        LOGGER.info(payloadValue);

        assertNotNull(payloadValue);
    }

    @Test
    public void executeMultiMatchOperation() throws Exception {
        String payloadValue = (String) flowRunner("testMultiMatchFlow").run().getMessage().getPayload().getValue();
        LOGGER.info(payloadValue);

        assertNotNull(payloadValue);
    }

    @Test
    public void executSearchMatchPhraseOperation() throws Exception {
        String payloadValue = (String) flowRunner("testSearchMatchPhraseFlow").run().getMessage().getPayload().getValue();
        LOGGER.info(payloadValue);

        assertNotNull(payloadValue);
    }

    @Test
    public void executSearchMatchPrefixOperation() throws Exception {
        String payloadValue = (String) flowRunner("testSearchMatchPrefixFlow").run().getMessage().getPayload().getValue();
        LOGGER.info(payloadValue);

        assertNotNull(payloadValue);
    }

    @Test
    public void executSearchCommomTermsOperation() throws Exception {
        String payloadValue = (String) flowRunner("testSearchCommomTermsFlow").run().getMessage().getPayload().getValue();
        LOGGER.info(payloadValue);

        assertNotNull(payloadValue);
    }

    @Test
    public void executSearchQueryStringOperation() throws Exception {
        String payloadValue = (String) flowRunner("testSearchQueryStringFlow").run().getMessage().getPayload().getValue();
        LOGGER.info(payloadValue);

        assertNotNull(payloadValue);
    }

    @Test
    public void executSearchSimpleQueryStringOperation() throws Exception {
        String payloadValue = (String) flowRunner("testSearchSimpleQueryStringFlow").run().getMessage().getPayload().getValue();
        LOGGER.info(payloadValue);

        assertNotNull(payloadValue);
    }

    @Test
    public void executSearchUsingJsonRequestOperation() throws Exception {
        String payloadValue = (String) flowRunner("testSearchUsingJsonRequestFlow").run().getMessage().getPayload().getValue();
        LOGGER.info(payloadValue);

        assertNotNull(payloadValue);
    }

    @Test
    public void executSearchScrollInitOperation() throws Exception {
        String payloadValue = (String) flowRunner("testSearchScrollInitFlow").run().getMessage().getPayload().getValue();
        LOGGER.info(payloadValue);
        assertNotNull(payloadValue);
    }

    @Test
    public void executSearchScrollOperation() throws Exception {
        String payloadValue = (String) flowRunner("testSearchScrollFlow").run().getMessage().getPayload().getValue();
        LOGGER.info(payloadValue);
        assertNotNull(payloadValue);
    }

    @Test
    public void executClearScrollOperation() throws Exception {
        String payloadValue = (String) flowRunner("testClearScrollFlow").run().getMessage().getPayload().getValue();
        LOGGER.info(payloadValue);
        assertNotNull(payloadValue);
    }

    @Test
    public void executSearchWithoutIndexOperation() throws Exception {
        String payloadValue = (String) flowRunner("testSearchWithoutIndexFlow").run().getMessage().getPayload().getValue();
        LOGGER.info(payloadValue);
        assertNotNull(payloadValue);
    }

    @Test
    public void executSearchCommomTermsWithDefaultOperation() throws Exception {
        String payloadValue = (String) flowRunner("SearchCommomTermsWithDefaultFlow").run().getMessage().getPayload().getValue();
        LOGGER.info(payloadValue);
        assertNotNull(payloadValue);
    }

    @Test
    public void executSearchMatchTermsWithDefaultOperation() throws Exception {
        String payloadValue = (String) flowRunner("testSearchMatchWithDefaultFlow").run().getMessage().getPayload().getValue();
        LOGGER.info(payloadValue);
        assertNotNull(payloadValue);
    }

    @Test
    public void executSearchMatchPhraseWithDefaultOperation() throws Exception {
        String payloadValue = (String) flowRunner("testSearchMatchPhraseWithDefaultFlow").run().getMessage().getPayload().getValue();
        LOGGER.info(payloadValue.toString());
        assertNotNull(payloadValue);
    }

    @Test
    public void executMultiMatchWithDefaultOperation() throws Exception {
        String payloadValue = (String) flowRunner("testMultiMatchWithDefaultFlow").run().getMessage().getPayload().getValue();
        LOGGER.info(payloadValue);
        assertNotNull(payloadValue);
    }

    @Test
    public void executSearchSimpleQueryStringWithDefaultOperation() throws Exception {
        String payloadValue = (String) flowRunner("testSearchSimpleQueryStringWithDefaultFlow").run().getMessage().getPayload().getValue();
        LOGGER.info(payloadValue);
        assertNotNull(payloadValue);
    }

    @Test
    public void executtestSearchQueryStringWithDefaultOperation() throws Exception {
        String payloadValue = (String) flowRunner("testSearchQueryStringWithDefaultFlow").run().getMessage().getPayload().getValue();
        LOGGER.info(payloadValue);
        assertNotNull(payloadValue);
    }

    @After
    public void tearDown() throws Exception {
        flowRunner("tearDownFlow").run();
    }
}
