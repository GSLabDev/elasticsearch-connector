package com.mulesoft.connectors.elasticsearch.Functional;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.mule.runtime.api.connection.ConnectionValidationResult;

import com.mulesoft.connectors.elasticsearch.internal.connection.ElasticsearchConnection;
import com.mulesoft.connectors.elasticsearch.internal.connection.provider.HttpConnectionProvider;


public class ElasticSearchFunctionsTestCase {
	
	
	@Test
	public void testElasticsearchConnectionWithException() {
		
		ElasticsearchConnection connection1 = new ElasticsearchConnection("host", 000);
		ElasticsearchConnection connection = new ElasticsearchConnection("host", 000, "username","password");
		assertNotNull(connection);
		assertNotNull(connection1);
		
	}
	@Test
	public void testElasticsearchConnectionInvalidate() {
		ElasticsearchConnection connection = new ElasticsearchConnection("127.0.0.1", 200);
		HttpConnectionProvider connectionProvider = new HttpConnectionProvider();
		ConnectionValidationResult ConnectionValidationResult = connectionProvider.validate(connection);
		
	}
  

}
