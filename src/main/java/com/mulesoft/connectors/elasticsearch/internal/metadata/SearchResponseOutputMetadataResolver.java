package com.mulesoft.connectors.elasticsearch.internal.metadata;

import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.metadata.resolving.OutputStaticTypeResolver;

import org.mule.metadata.api.builder.BaseTypeBuilder;
import org.mule.metadata.api.builder.ObjectTypeBuilder;

import static org.mule.metadata.api.model.MetadataFormat.JSON;

public class SearchResponseOutputMetadataResolver extends OutputStaticTypeResolver {

    @Override
    public MetadataType getStaticMetadata() {
        final ObjectTypeBuilder objectBuilder = BaseTypeBuilder.create(JSON).objectType().id("SearchResponse");
        
        objectBuilder.addField().key("aggregations").value().arrayType().of().objectType();
        
        final ObjectTypeBuilder clustersObjectBuilder = objectBuilder.addField().key("clusters").value().objectType();
        clustersObjectBuilder.addField().key("skipped").value().numberType();
        clustersObjectBuilder.addField().key("successful").value().numberType();
        clustersObjectBuilder.addField().key("total").value().numberType();
        
        objectBuilder.addField().key("failedShards").value().numberType();
        objectBuilder.addField().key("hits").value().arrayType().of().arrayType().of().arrayType().of().objectType();
        objectBuilder.addField().key("numReducePhases").value().numberType();
        objectBuilder.addField().key("profileResults").value().objectType();
        objectBuilder.addField().key("scrollId").value().stringType();
        
        final ObjectTypeBuilder shardFailuresObjectBuilder = objectBuilder.addField().key("shardFailures").value().arrayType().of().objectType();
        OutputMetadataResolver.getNestedCauseObjectTypeBuilder(shardFailuresObjectBuilder, false);

        objectBuilder.addField().key("skippedShards").value().numberType();
        objectBuilder.addField().key("successfulShards").value().numberType();
        
        final ObjectTypeBuilder suggestObjectBuilder = objectBuilder.addField().key("suggest").value().arrayType().of().arrayType().of().arrayType().of().objectType();
        suggestObjectBuilder.addField().key("highlighted").value().objectType();
        suggestObjectBuilder.addField().key("score").value().numberType();
        suggestObjectBuilder.addField().key("text").value().objectType();        
        
        objectBuilder.addField().key("timedOut").value().booleanType();
        
        final ObjectTypeBuilder tookObjectBuilder = objectBuilder.addField().key("took").value().objectType();
        tookObjectBuilder.addField().key("days").value().numberType();
        tookObjectBuilder.addField().key("daysFrac").value().numberType();
        tookObjectBuilder.addField().key("hours").value().numberType();
        tookObjectBuilder.addField().key("hoursFrac").value().numberType();
        tookObjectBuilder.addField().key("micros").value().numberType();
        tookObjectBuilder.addField().key("microsFrac").value().numberType();
        tookObjectBuilder.addField().key("millis").value().numberType();
        tookObjectBuilder.addField().key("millisFrac").value().numberType();
        tookObjectBuilder.addField().key("minutes").value().numberType();
        tookObjectBuilder.addField().key("minutesFrac").value().numberType();
        tookObjectBuilder.addField().key("nanos").value().numberType();
        tookObjectBuilder.addField().key("seconds").value().numberType();
        tookObjectBuilder.addField().key("secondsFrac").value().numberType();
        tookObjectBuilder.addField().key("stringRep").value().stringType();
        
        objectBuilder.addField().key("totalShards").value().numberType();
        
        return objectBuilder.build();
    }
}
