/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
/**
 *
 */
package org.mule.extension.elastic.internal.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.message.BasicHeader;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 * 
 *         ElasticSearch util functions
 */
public class ElasticsearchUtils {

    private ElasticsearchUtils() {
    }

    public static BasicHeader getContentTypeJsonHeader() {
        return new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json");
    }

    public static String readFileToString(String filePath) throws IOException {
        return FileUtils.readFileToString(new File(filePath));
    }

}
