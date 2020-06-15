package com.mulesoft.connectors.elasticsearch.internal.metadata;

import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.metadata.resolving.OutputStaticTypeResolver;
import org.mule.metadata.api.builder.BaseTypeBuilder;
import org.mule.metadata.api.builder.ObjectTypeBuilder;

import static org.mule.metadata.api.model.MetadataFormat.JSON;

public class DeleteResponseOutputMetadataResolver extends OutputStaticTypeResolver {
    
    @Override
    public MetadataType getStaticMetadata() {
      return OutputMetadataResolver.getResponseStaticMetadata("DeleteResponse");
    }
}
