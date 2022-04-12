package com.mulesoft.connectors.elasticsearch.Functional;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import com.mulesoft.connectors.elasticsearch.internal.connection.ElasticsearchConnection;

public class ElasticSearchFunctionsTestCase {
	
	
	@Test
	public void testElasticsearchConnectionWithException() {
		
		ElasticsearchConnection connection1 = new ElasticsearchConnection("host", 000);
		ElasticsearchConnection connection = new ElasticsearchConnection("host", 000, "username","password");
//		ElasticsearchConnection connection2 = new ElasticsearchConnection("host", 00,"userName", "password", String trustStoreType,
//				String trustStorePath, String trustStorePassword);
		assertNotNull(connection);
		assertNotNull(connection1);
		
	}
  

}
