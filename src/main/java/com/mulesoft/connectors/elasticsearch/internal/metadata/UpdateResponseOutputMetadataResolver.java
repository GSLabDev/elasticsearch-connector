/**
 * Copyright (c) 2003-2020, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.2, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.internal.metadata;

import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.metadata.resolving.OutputStaticTypeResolver;

import org.mule.metadata.api.builder.BaseTypeBuilder;
import org.mule.metadata.api.builder.ObjectTypeBuilder;

import static org.mule.metadata.api.model.MetadataFormat.JSON;

public class UpdateResponseOutputMetadataResolver extends OutputStaticTypeResolver {
    @Override
    public MetadataType getStaticMetadata() {
        final ObjectTypeBuilder objectBuilder = BaseTypeBuilder.create(JSON).objectType().id("UpdateResponse");
        OutputMetadataResolver.getResponseStaticMetadata(objectBuilder);

        final ObjectTypeBuilder getResultObjectBuilder = objectBuilder.addField().key("getResult").value().objectType();        
        getResultObjectBuilder.addField().key("id").value().stringType();
        getResultObjectBuilder.addField().key("index").value().stringType();
        getResultObjectBuilder.addField().key("primaryTerm").value().numberType();
        getResultObjectBuilder.addField().key("seqNo").value().numberType();
        getResultObjectBuilder.addField().key("source").value().objectType();
        getResultObjectBuilder.addField().key("type").value().stringType();
        getResultObjectBuilder.addField().key("version").value().numberType();
        getResultObjectBuilder.addField().key("exists").value().booleanType();
        getResultObjectBuilder.addField().key("fields").value().objectType();
        getResultObjectBuilder.addField().key("documentFields").value().objectType();
        getResultObjectBuilder.addField().key("metadataFields").value().objectType();
        
        return objectBuilder.build();
    }
  }
