/**
 * Copyright (c) 2003-2020, Great Software Laboratory Pvt. Ltd. The software in this package is published under the terms of the Commercial Free Software license V.2, a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.elasticsearch.api.querytype;

import org.elasticsearch.common.unit.Fuzziness;

public enum FuzzinessEnum {
    AUTO,
    ZERO,
    ONE,
    TWO;
    
    public Fuzziness getElasticsearchFuzziness() {
        switch (this) {
            case AUTO:
                return Fuzziness.AUTO;
            case ZERO:
                return Fuzziness.ZERO;
            case ONE:
                return Fuzziness.ONE;
            case TWO:
                return Fuzziness.TWO;
            default:
                return null;
        }
    }
}
