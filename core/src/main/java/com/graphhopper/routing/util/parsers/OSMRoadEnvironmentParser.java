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
import com.graphhopper.routing.ev.EnumEncodedValue;
import com.graphhopper.routing.ev.RoadEnvironment;
import com.graphhopper.storage.IntsRef;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.graphhopper.routing.ev.RoadEnvironment.*;

public class OSMRoadEnvironmentParser implements TagParser {

    private final EnumEncodedValue<RoadEnvironment> roadEnvEnc;

    public OSMRoadEnvironmentParser(EnumEncodedValue<RoadEnvironment> roadEnvEnc) {
        this.roadEnvEnc = roadEnvEnc;
    }

    @Override
    public void handleWayTags(IntsRef edgeFlags, ReaderWay readerWay, IntsRef relationFlags) {
        List<Map<String, Object>> nodeTags = readerWay.getTag("node_tags", Collections.emptyList());
        // a barrier edge has the restriction in both nodes and the tags are the same
        if (readerWay.hasTag("gh:barrier_edge") && nodeTags.get(0).containsKey("ford")) {
            roadEnvEnc.setEnum(false, edgeFlags, FORD);
            return;
        }

        RoadEnvironment roadEnvironment = OTHER;
        if ((readerWay.hasTag("route", "ferry") && !readerWay.hasTag("ferry", "no")) ||
                // TODO shuttle_train is sometimes also used in relations, e.g. https://www.openstreetmap.org/relation/1932780
                readerWay.hasTag("route", "shuttle_train") && !readerWay.hasTag("shuttle_train", "no"))
            roadEnvironment = FERRY;
        else if (readerWay.hasTag("bridge") && !readerWay.hasTag("bridge", "no"))
            roadEnvironment = BRIDGE;
        else if (readerWay.hasTag("tunnel") && !readerWay.hasTag("tunnel", "no"))
            roadEnvironment = TUNNEL;
        else if (readerWay.hasTag("ford") || readerWay.hasTag("highway", "ford"))
            roadEnvironment = FORD;
        else if (readerWay.hasTag("highway"))
            roadEnvironment = ROAD;

        if (roadEnvironment != OTHER)
            roadEnvEnc.setEnum(false, edgeFlags, roadEnvironment);
    }
}
