package com.mulesoft.connectors.elasticsearch.internal.metadata;

import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.metadata.resolving.OutputStaticTypeResolver;
import org.mule.metadata.api.builder.BaseTypeBuilder;
import org.mule.metadata.api.builder.ObjectTypeBuilder;

import static org.mule.metadata.api.model.MetadataFormat.JSON;

public class GetResponseOutputMetadataResolver extends OutputStaticTypeResolver {

    @Override
    public MetadataType getStaticMetadata() {

        final ObjectTypeBuilder objectBuilder = BaseTypeBuilder.create(JSON).objectType().id("GetResponse");
        objectBuilder.addField().key("id").value().stringType();
        objectBuilder.addField().key("index").value().stringType();
        objectBuilder.addField().key("primaryTerm").value().numberType();
        objectBuilder.addField().key("seqNo").value().numberType();
        objectBuilder.addField().key("sourceAsString").value().stringType();
        objectBuilder.addField().key("type").value().stringType();
        objectBuilder.addField().key("version").value().numberType();
        objectBuilder.addField().key("found").value().booleanType();

        return objectBuilder.build();
    }
}
