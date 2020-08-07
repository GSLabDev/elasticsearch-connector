/**
 * Copyright (c) 2003-2020, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.2, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.internal.metadata;

import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.metadata.resolving.OutputStaticTypeResolver;
import org.mule.metadata.api.builder.BaseTypeBuilder;
import org.mule.metadata.api.builder.ObjectTypeBuilder;

import static org.mule.metadata.api.model.MetadataFormat.JSON;

public class MainResponseOutputMetadataResolver extends OutputStaticTypeResolver {
    @Override
    public MetadataType getStaticMetadata() {      
      final ObjectTypeBuilder objectBuilder = BaseTypeBuilder.create(JSON).objectType().id("MainResponse");
      
      objectBuilder.addField().key("clusterName").value().stringType();
      objectBuilder.addField().key("clusterUuid").value().stringType();
      objectBuilder.addField().key("nodeName").value().stringType();
      objectBuilder.addField().key("tagline").value().stringType();
      
      final ObjectTypeBuilder versionObjectBuilder = objectBuilder.addField().key("version").value().objectType();
      versionObjectBuilder.addField().key("buildDate").value().stringType();
      versionObjectBuilder.addField().key("buildFlavor").value().stringType();
      versionObjectBuilder.addField().key("buildHash").value().stringType();
      versionObjectBuilder.addField().key("buildType").value().stringType();
      versionObjectBuilder.addField().key("luceneVersion").value().stringType();
      versionObjectBuilder.addField().key("minimumIndexCompatibilityVersion").value().stringType();
      versionObjectBuilder.addField().key("minimumWireCompatibilityVersion").value().stringType();
      versionObjectBuilder.addField().key("number").value().stringType();
      versionObjectBuilder.addField().key("snapshot").value().booleanType();  
      
      return objectBuilder.build();
    }
  }
