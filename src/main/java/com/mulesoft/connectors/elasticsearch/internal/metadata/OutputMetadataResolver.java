/**
 * Copyright (c) 2003-2020, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.2, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.internal.metadata;

import org.mule.metadata.api.builder.ObjectTypeBuilder;

public class OutputMetadataResolver {

	private static final String CAUSE = "cause";

	private OutputMetadataResolver() {
	}

	public static void getResponseStaticMetadata(ObjectTypeBuilder objectBuilder) {
		objectBuilder.addField().key("forcedRefresh").value().booleanType();
		objectBuilder.addField().key("id").value().stringType();
		objectBuilder.addField().key("index").value().stringType();
		objectBuilder.addField().key("primaryTerm").value().numberType();
		objectBuilder.addField().key("result").value().stringType();
		objectBuilder.addField().key("seqNo").value().numberType();

		final ObjectTypeBuilder shardIdObjectBuilder = objectBuilder.addField().key("shardId").value().objectType();
		shardIdObjectBuilder.addField().key("id").value().numberType();

		final ObjectTypeBuilder indexObjectBuilder = shardIdObjectBuilder.addField().key("index").value().objectType();
		indexObjectBuilder.addField().key("UUID").value().stringType();
		indexObjectBuilder.addField().key("name").value().stringType();

		shardIdObjectBuilder.addField().key("indexName").value().stringType();

		final ObjectTypeBuilder shardInfoObjectBuilder = objectBuilder.addField().key("shardInfo").value().objectType();
		shardInfoObjectBuilder.addField().key("failed").value().numberType();

		final ObjectTypeBuilder failuresObjectBuilder = shardInfoObjectBuilder.addField().key("failures").value()
				.arrayType().of().objectType();
		getNestedCauseObjectTypeBuilder(failuresObjectBuilder, false);

		shardInfoObjectBuilder.addField().key("successful").value().numberType();
		shardInfoObjectBuilder.addField().key("total").value().numberType();

		objectBuilder.addField().key("type").value().stringType();
		objectBuilder.addField().key("version").value().numberType();
	}

	static void getNestedCauseObjectTypeBuilder(ObjectTypeBuilder typeBuilder, boolean nodeId) {
		final ObjectTypeBuilder causeObjectBuilder = typeBuilder.addField().key(CAUSE).value().objectType();
		causeObjectBuilder.addField().key(CAUSE).value(causeObjectBuilder);

		addFieldsInCauseObjectTypeBuilder(causeObjectBuilder);

		final ObjectTypeBuilder suppressedObjectBuilder = causeObjectBuilder.addField().key("suppressed").value()
				.arrayType().of().objectType();
		getCauseObjectTypeBuilder(suppressedObjectBuilder);

		if (nodeId) {
			typeBuilder.addField().key("nodeId").value().stringType();
		}
	}

	static void getCauseObjectTypeBuilder(ObjectTypeBuilder typeBuilder) {
		typeBuilder.addField().key(CAUSE).value(typeBuilder);

		addFieldsInCauseObjectTypeBuilder(typeBuilder);

		typeBuilder.addField().key("suppressed").value().arrayType().of().objectType();
	}

	private static void addFieldsInCauseObjectTypeBuilder(ObjectTypeBuilder objectTypeBuilder) {
		objectTypeBuilder.addField().key("localizedMessage").value().stringType();
		objectTypeBuilder.addField().key("message").value().stringType();

		final ObjectTypeBuilder stackTraceObjectBuilder = objectTypeBuilder.addField().key("stackTrace").value()
				.arrayType().of().objectType();
		stackTraceObjectBuilder.addField().key("className").value().stringType();
		stackTraceObjectBuilder.addField().key("fileName").value().stringType();
		stackTraceObjectBuilder.addField().key("lineNumber").value().numberType();
		stackTraceObjectBuilder.addField().key("methodName").value().stringType();
		stackTraceObjectBuilder.addField().key("nativeMethod").value().booleanType();
	}
}
