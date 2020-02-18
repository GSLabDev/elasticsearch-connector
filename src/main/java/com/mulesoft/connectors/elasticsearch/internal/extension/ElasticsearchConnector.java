/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.internal.extension;

import org.mule.runtime.api.meta.Category;
import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.SubTypeMapping;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;
import org.mule.runtime.extension.api.annotation.error.ErrorTypes;
import org.mule.runtime.extension.api.annotation.error.Throws;
import org.mule.runtime.extension.api.annotation.license.RequiresEnterpriseLicense;

import com.mulesoft.connectors.elasticsearch.api.querytype.CommonTermsQuery;
import com.mulesoft.connectors.elasticsearch.api.querytype.MatchAllQuery;
import com.mulesoft.connectors.elasticsearch.api.querytype.MatchPhrasePrefixQuery;
import com.mulesoft.connectors.elasticsearch.api.querytype.MatchPhraseQuery;
import com.mulesoft.connectors.elasticsearch.api.querytype.MatchQuery;
import com.mulesoft.connectors.elasticsearch.api.querytype.MultiMatchQuery;
import com.mulesoft.connectors.elasticsearch.api.querytype.Query;
import com.mulesoft.connectors.elasticsearch.api.querytype.QueryStringQuery;
import com.mulesoft.connectors.elasticsearch.api.querytype.SimpleQueryString;
import com.mulesoft.connectors.elasticsearch.internal.connection.provider.HttpConnectionProvider;
import com.mulesoft.connectors.elasticsearch.internal.connection.provider.HttpsConnectionProvider;
import com.mulesoft.connectors.elasticsearch.internal.error.ElasticsearchErrorTypes;
import com.mulesoft.connectors.elasticsearch.internal.error.providers.ExecutionErrorTypesProvider;
import com.mulesoft.connectors.elasticsearch.internal.operations.DocumentOperations;
import com.mulesoft.connectors.elasticsearch.internal.operations.IndexOperations;
import com.mulesoft.connectors.elasticsearch.internal.operations.InfoOperation;
import com.mulesoft.connectors.elasticsearch.internal.operations.SearchOperations;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 * 
 *         ElasticSearch Connector provides integration with the ElasticSearch server from a mule flow. It supports HTTP, HTTP with basic authentication and HTTPS connection.
 */
@Xml(prefix = "elasticsearch")
@ErrorTypes(ElasticsearchErrorTypes.class)
@Extension(name = "Elasticsearch", category = Category.CERTIFIED, vendor = "Great Software Laboratory Pvt. Ltd.")
@RequiresEnterpriseLicense(allowEvaluationLicense = true)

@SubTypeMapping(baseType = Query.class, subTypes = {
    MatchQuery.class,
    MultiMatchQuery.class,
    MatchAllQuery.class,
    MatchPhraseQuery.class,
    MatchPhrasePrefixQuery.class,
    CommonTermsQuery.class,
    QueryStringQuery.class,
    SimpleQueryString.class
})

@ConnectionProviders({
    HttpConnectionProvider.class,
    HttpsConnectionProvider.class
})

@Operations({
    IndexOperations.class,
    SearchOperations.class,
    DocumentOperations.class,
    InfoOperation.class
})

@Throws(ExecutionErrorTypesProvider.class)
public class ElasticsearchConnector {

}
