/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.extension.elastic.internal;

import org.mule.extension.elastic.api.querytype.CommonTermsQuery;
import org.mule.extension.elastic.api.querytype.MatchAllQuery;
import org.mule.extension.elastic.api.querytype.MatchPhrasePrefixQuery;
import org.mule.extension.elastic.api.querytype.MatchPhraseQuery;
import org.mule.extension.elastic.api.querytype.MatchQuery;
import org.mule.extension.elastic.api.querytype.MultiMatchQuery;
import org.mule.extension.elastic.api.querytype.Query;
import org.mule.extension.elastic.api.querytype.QueryStringQuery;
import org.mule.extension.elastic.api.querytype.SimpleQueryString;
import org.mule.extension.elastic.internal.connection.HttpBasicAuthConnectionProvider;
import org.mule.extension.elastic.internal.connection.HttpConnectionProvider;
import org.mule.extension.elastic.internal.connection.HttpsConnectionProvider;
import org.mule.extension.elastic.internal.operations.DocumentOperations;
import org.mule.extension.elastic.internal.operations.IndexOperations;
import org.mule.extension.elastic.internal.operations.InfoOperation;
import org.mule.extension.elastic.internal.operations.SearchOperations;
import org.mule.runtime.api.meta.Category;
import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.SubTypeMapping;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;
import org.mule.runtime.extension.api.annotation.license.RequiresEnterpriseLicense;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 * 
 *         ElasticSearch Connector provides integration with the ElasticSearch server from a mule flow. It supports HTTP, HTTP with basic authentication and HTTPS connection.
 */
@Xml(prefix = "elasticsearch")
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
    HttpBasicAuthConnectionProvider.class,
    HttpsConnectionProvider.class
})

@Operations({
    IndexOperations.class,
    SearchOperations.class,
    DocumentOperations.class,
    InfoOperation.class
})

public class ElasticsearchConnector {

}
