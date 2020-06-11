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
