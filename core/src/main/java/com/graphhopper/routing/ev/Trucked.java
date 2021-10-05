package com.graphhopper.routing.ev;

import com.graphhopper.util.Helper;

/**
 * Trucked as decimal value.
 */
public class Trucked {
    public static final String KEY = "trucked";

    public static DecimalEncodedValue create() {
        return new UnsignedDecimalEncodedValue(KEY, 8, 0.0, Double.POSITIVE_INFINITY, false);
    }
}