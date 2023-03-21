/*
 *  Licensed to GraphHopper GmbH under one or more contributor
 *  license agreements. See the NOTICE file distributed with this work for
 *  additional information regarding copyright ownership.
 *
 *  GraphHopper GmbH licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except in
 *  compliance with the License. You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.graphhopper.routing.util.parsers;

import com.graphhopper.reader.ReaderWay;
import com.graphhopper.routing.ev.EncodedValue;
import com.graphhopper.routing.ev.EncodedValueLookup;
import com.graphhopper.routing.ev.IntEncodedValue;
import com.graphhopper.routing.ev.Trucked;
import com.graphhopper.storage.IntsRef;

import java.util.List;
import java.lang.Integer;
import java.lang.NumberFormatException;

public class TruckedParser implements TagParser {
    private final IntEncodedValue truckedEnc;

    public TruckedParser(IntEncodedValue truckedEnc) {
        this.truckedEnc = truckedEnc;
    }

    @Override
    public void handleWayTags(IntsRef edgeFlags, ReaderWay readerWay, IntsRef relationFlags) {
        int value = 0;

        try {
            value = Integer.parseInt(readerWay.getTag("trucked"));
        } catch (NumberFormatException nfe) {
            // nothing to do
        }

        if (value > 0) {
            truckedEnc.setInt(false, edgeFlags, value);
        }
    }
}
