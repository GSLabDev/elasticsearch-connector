/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.extension.elastic.api;

import org.mule.runtime.api.meta.model.display.PathModel.Location;
import org.mule.runtime.api.meta.model.display.PathModel.Type;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Password;
import org.mule.runtime.extension.api.annotation.param.display.Path;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 * 
 *         TrustStore configuration parameters
 *
 */
public class TrustStoreConfiguration {

    /**
     * Type of the TrustStore
     */
    @Parameter
    @DisplayName("Type")
    @Optional(defaultValue = "jks")
    private String trustStoreType;

    /**
     * TrustStore file path
     */

    @Parameter
    @Path(type = Type.FILE, acceptedFileExtensions = "jks", location = Location.ANY)
    @DisplayName("Path")
    private String trustStorePath;

    /**
     * Trust store password
     */
    @Parameter
    @Password
    @DisplayName("Password")
    private String trustStorePassword;

    public String getTrustStoreType() {
        return trustStoreType;
    }

    public String getTrustStorePath() {
        return trustStorePath;
    }

    public String getTrustStorePassword() {
        return trustStorePassword;
    }

}
