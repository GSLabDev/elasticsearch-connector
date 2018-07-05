/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.extension.elastic.api;

import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Password;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 * 
 *         User authentication configuration parameters
 */
public class UserConfiguration {

    /**
     * Elasticsearch instance username. Keep this blank for anonymous user.
     */
    @Parameter
    @Optional
    @DisplayName("User Name")
    private String userName;

    /**
     * Elasticsearch instance password. Keep this blank for anonymous user.
     */
    @Parameter
    @Optional
    @DisplayName("Password")
    @Password
    private String password;

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

}