package com.graphhopper.routing.ev;

import com.graphhopper.util.Helper;

/**
 * Trucked as numerical value.
 */
public class Trucked {
    public static final String KEY = "trucked";

    public static IntEncodedValue create() {
        return new IntEncodedValueImpl(KEY, 31, false);
    }
}
