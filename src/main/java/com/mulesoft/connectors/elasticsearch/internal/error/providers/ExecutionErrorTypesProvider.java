/**
 * Copyright © 2019 Braintree, a service of PayPal Inc. The software in this package is published under the terms of the Commercial Free Software license, a copy of which has been included with this distribution in the LICENSE.md file
 */
package com.mulesoft.connectors.elasticsearch.internal.error.providers;

import java.util.Set;

import org.mule.runtime.extension.api.error.ErrorTypeDefinition;

import com.mulesoft.connectors.elasticsearch.internal.error.ElasticsearchErrorTypes;

public class ExecutionErrorTypesProvider extends ElasticsearchErrorTypesProvider {

    @Override
    public void addErrors(Set<ErrorTypeDefinition> errors) {
        errors.add(ElasticsearchErrorTypes.EXECUTION);
    }

}
