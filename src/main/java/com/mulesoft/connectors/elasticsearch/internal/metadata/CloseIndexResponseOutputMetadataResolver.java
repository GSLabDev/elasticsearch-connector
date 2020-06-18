package com.mulesoft.connectors.elasticsearch.internal.metadata;

import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.metadata.resolving.OutputStaticTypeResolver;
import org.mule.metadata.api.builder.BaseTypeBuilder;
import org.mule.metadata.api.builder.ObjectTypeBuilder;

import static org.mule.metadata.api.model.MetadataFormat.JSON;

public class CloseIndexResponseOutputMetadataResolver extends OutputStaticTypeResolver {

    @Override
    public MetadataType getStaticMetadata() {
        final ObjectTypeBuilder objectBuilder = BaseTypeBuilder.create(JSON).objectType().id("CloseIndexResponse");
        objectBuilder.addField().key("acknowledged").value().booleanType();
        
        final ObjectTypeBuilder indicesObjectBuilder = objectBuilder.addField().key("indices").value().arrayType().of().objectType();
        
        final ObjectTypeBuilder exceptionObjectBuilder = indicesObjectBuilder.addField().key("exception").value().objectType();
        OutputMetadataResolver.getCauseObjectTypeBuilder(exceptionObjectBuilder);
        
        indicesObjectBuilder.addField().key("index").value().stringType();
        
        final ObjectTypeBuilder shardsObjectBuilder = indicesObjectBuilder.addField().key("shards").value().arrayType().of().objectType();
        final ObjectTypeBuilder failuresObjectBuilder = shardsObjectBuilder.addField().key("failures").value().arrayType().of().objectType();
        OutputMetadataResolver.getNestedCauseObjectTypeBuilder(failuresObjectBuilder, true);
        
        shardsObjectBuilder.addField().key("id").value().numberType();        
        
        objectBuilder.addField().key("shardsAcknowledged").value().booleanType();

        return objectBuilder.build();
    }
}