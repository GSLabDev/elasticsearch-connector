/**
 * Copyright (c) 2003-2020, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.2, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.internal.metadata;

import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.metadata.resolving.OutputStaticTypeResolver;
import org.mule.metadata.api.builder.BaseTypeBuilder;
import org.mule.metadata.api.builder.ObjectTypeBuilder;

import static org.mule.metadata.api.model.MetadataFormat.JSON;

public class ResponseOutputMetadataResolver extends OutputStaticTypeResolver {
    
    @Override
    public MetadataType getStaticMetadata() {
      final ObjectTypeBuilder objectBuilder = BaseTypeBuilder.create(JSON).objectType().id("Response");
            
      objectBuilder.addField().key("headers").value().arrayType().of().objectType();
      
      objectBuilder.addField().key("entity").value().stringType();
      
      final ObjectTypeBuilder hostObjectBuilder = objectBuilder.addField().key("host").value().objectType();
      
      final ObjectTypeBuilder hostAddressObjectBuilder = hostObjectBuilder.addField().key("address").value().objectType();
      hostAddressObjectBuilder.addField().key("MCGlobal").value().booleanType();
      hostAddressObjectBuilder.addField().key("MCLinkLocal").value().booleanType();
      hostAddressObjectBuilder.addField().key("MCNodeLocal").value().booleanType();
      hostAddressObjectBuilder.addField().key("MCOrgLocal").value().booleanType();
      hostAddressObjectBuilder.addField().key("MCSiteLocal").value().booleanType();
      hostAddressObjectBuilder.addField().key("address").value().binaryType();
      hostAddressObjectBuilder.addField().key("anyLocalAddress").value().booleanType();
      hostAddressObjectBuilder.addField().key("canonicalHostName").value().stringType();
      hostAddressObjectBuilder.addField().key("hostAddress").value().stringType();
      hostAddressObjectBuilder.addField().key("hostName").value().stringType();
      hostAddressObjectBuilder.addField().key("linkLocalAddress").value().booleanType();
      hostAddressObjectBuilder.addField().key("loopbackAddress").value().booleanType();
      hostAddressObjectBuilder.addField().key("multicastAddress").value().booleanType();
      hostAddressObjectBuilder.addField().key("siteLocalAddress").value().booleanType();
      
      hostObjectBuilder.addField().key("hostName").value().stringType();
      hostObjectBuilder.addField().key("port").value().numberType();
      hostObjectBuilder.addField().key("schemeName").value().stringType();
           
      objectBuilder.addField().key("requestLine").value().objectType();
      objectBuilder.addField().key("statusLine").value().objectType();
      objectBuilder.addField().key("warnings").value().arrayType().of().stringType();
      
      return objectBuilder.build();
    }
}
