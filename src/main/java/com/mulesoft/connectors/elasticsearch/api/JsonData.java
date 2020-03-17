/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.api;

import org.mule.runtime.api.meta.model.display.PathModel.Location;
import org.mule.runtime.api.meta.model.display.PathModel.Type;
import org.mule.runtime.extension.api.annotation.dsl.xml.ParameterDsl;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Path;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.mule.runtime.extension.api.annotation.param.display.Text;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 * 
 *         JSON file path or JSON text exclusive parameter option class
 */
public class JsonData {

    /**
     * Provide the JSON file path
     */
    @Parameter
    @Optional
    @Path(type = Type.FILE, acceptedFileExtensions = "json", location = Location.ANY)
    @ParameterDsl(allowReferences = false)
    @DisplayName("File")
    @Summary("Provide the JSON file path. Either of the fields JSON file path or JSON text must be set.")
    private String jsonfile;

    /**
     * Provide the JSON string
     */
    @Parameter
    @Optional
    @Text
    @DisplayName("Text")
    @Summary("Provide the JSON string. Either of the fields JSON file path or JSON text must be set.")
    private String jsonText;

    public String getJsonfile() {
        return jsonfile;
    }

    public String getJsonText() {
        return jsonText;
    }
}