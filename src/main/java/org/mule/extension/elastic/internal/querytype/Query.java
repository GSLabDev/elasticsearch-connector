/**
 * Copyright (c) 2003-2017, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.1, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.extension.elastic.internal.querytype;

import org.elasticsearch.index.query.QueryBuilder;

/**
 * @author Great Software Laboratory Pvt. Ltd.
 * 
 *         Base query class implemented by all elastic search query types
 */
public interface Query<T extends QueryBuilder> {

    public T getQuery();
}
