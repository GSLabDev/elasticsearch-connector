/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.index;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;
import org.mule.functional.junit4.MuleArtifactFunctionalTestCase;
/**
 * @author Great Software Laboratory Pvt. Ltd.
 *
 */
public class InfoTestCase extends MuleArtifactFunctionalTestCase {
    
    @Override
    protected String getConfigFile() {
        return "testElasticsearchOperations.xml";
    }
    
    @Test
    public void executeInfoOperation() {
        try {
            Object payloadValue = flowRunner("testInfoFlow").run().getMessage().getPayload();
            assertThat(payloadValue, notNullValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}