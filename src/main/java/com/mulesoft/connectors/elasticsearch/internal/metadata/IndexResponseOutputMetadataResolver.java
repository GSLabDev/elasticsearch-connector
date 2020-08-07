/**
 * Copyright (c) 2003-2020, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.2, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.internal.metadata;

import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.metadata.resolving.OutputStaticTypeResolver;

import org.mule.metadata.api.builder.BaseTypeBuilder;
import org.mule.metadata.api.builder.ObjectTypeBuilder;

import static org.mule.metadata.api.model.MetadataFormat.JSON;

public class IndexResponseOutputMetadataResolver extends OutputStaticTypeResolver {

    @Override
    public MetadataType getStaticMetadata() {
        final ObjectTypeBuilder objectBuilder = BaseTypeBuilder.create(JSON).objectType().id("IndexResponse");
        
        OutputMetadataResolver.getResponseStaticMetadata(objectBuilder);
        
        return objectBuilder.build();
    }
}
