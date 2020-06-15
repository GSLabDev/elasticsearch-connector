package com.mulesoft.connectors.elasticsearch.internal.metadata;

import static org.mule.metadata.api.model.MetadataFormat.JSON;

import org.mule.metadata.api.builder.BaseTypeBuilder;
import org.mule.metadata.api.builder.ObjectTypeBuilder;
import org.mule.metadata.api.model.MetadataType;

public class OutputMetadataResolver {

    public static MetadataType getResponseStaticMetadata(String keyName) {      
        final ObjectTypeBuilder objectBuilder = BaseTypeBuilder.create(JSON).objectType().id(keyName);
        
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
             
        final ObjectTypeBuilder failuresObjectBuilder = shardInfoObjectBuilder.addField().key("failures").value().arrayType().of().objectType();
        getCauseObjectTypeBuilder(failuresObjectBuilder);
              
        shardInfoObjectBuilder.addField().key("successful").value().numberType();
        shardInfoObjectBuilder.addField().key("total").value().numberType();
        
        objectBuilder.addField().key("type").value().stringType();
        objectBuilder.addField().key("version").value().numberType();
        
        return objectBuilder.build();
    }
    
    private static void getCauseObjectTypeBuilder(ObjectTypeBuilder typeBuilder) {
        final ObjectTypeBuilder causeObjectBuilder = typeBuilder.addField().key("cause").value().objectType();
        causeObjectBuilder.addField().key("cause").value(causeObjectBuilder);
        
        causeObjectBuilder.addField().key("localizedMessage").value().stringType();
        causeObjectBuilder.addField().key("message").value().stringType();
        
        final ObjectTypeBuilder stackTraceObjectBuilder = causeObjectBuilder.addField().key("stackTrace").value().arrayType().of().objectType();
        stackTraceObjectBuilder.addField().key("className").value().stringType();
        stackTraceObjectBuilder.addField().key("fileName").value().stringType();
        stackTraceObjectBuilder.addField().key("lineNumber").value().numberType();
        stackTraceObjectBuilder.addField().key("methodName").value().stringType();
        stackTraceObjectBuilder.addField().key("nativeMethod").value().booleanType();
      
        final ObjectTypeBuilder suppressedObjectBuilder = causeObjectBuilder.addField().key("suppressed").value().arrayType().of().objectType();
        suppressedObjectBuilder.addField().key("cause").value(causeObjectBuilder);
    }
}
