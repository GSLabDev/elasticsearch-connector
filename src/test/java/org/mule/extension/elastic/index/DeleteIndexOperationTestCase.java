/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.extension.elastic.index;

import static org.junit.Assert.assertTrue;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.junit.Before;
import org.junit.Test;
import org.mule.functional.junit4.MuleArtifactFunctionalTestCase;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 *
 */
public class DeleteIndexOperationTestCase extends MuleArtifactFunctionalTestCase {

    @Override
    protected String getConfigFile() {
        return "testElasticsearchOperations.xml";
    }

    /**
     * Setup the resources required to run the operation
     */
    @Before
    public void setup() {
        try {
            flowRunner("testCreateIndexFlow").run().getMessage().getPayload().getValue();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test the operation
     */
    @Test
    public void executeIndexDocumentOperation() {
        DeleteIndexResponse payloadValue;
        try {
            payloadValue = ((DeleteIndexResponse) flowRunner("testDeleteIndexFlow").run().getMessage().getPayload().getValue());
            assertTrue(payloadValue.isAcknowledged() == true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}