/**
 * Copyright (c) 2003-2020, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.2, a copy of which has been included with this distribution in the LICENSE.md file.
 */
/**
 *
 */
package com.mulesoft.connectors.elasticsearch.document;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.functional.junit4.MuleArtifactFunctionalTestCase;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 *
 */
public class GetDocumentTestCase extends MuleArtifactFunctionalTestCase {

    @Override
    protected String getConfigFile() {
        return "testElasticsearchOperations.xml";
    }

    /**
     * Setup the resources required to run the operation
     */
    @Before
    public void setup() throws InterruptedException {
        try {
            flowRunner("testCreateIndexFlow").run().getMessage().getPayload().getValue();
            flowRunner("indexDocumentFlow").run().getMessage().getPayload().getValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove the resources generated by the operation
     */
    @After
    public void tearDown() {
        try {
            flowRunner("testDeleteIndexFlow").run().getMessage().getPayload().getValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test the operation
     */
    @Test
    public void executeGetDocumentOperation() throws Exception {
        Object payloadValue = flowRunner("testGetDocumentFlow").run().getMessage().getPayload().getValue().toString();
        assertThat(payloadValue, notNullValue());
    }
}