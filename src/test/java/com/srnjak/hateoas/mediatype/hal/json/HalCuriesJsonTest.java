package com.srnjak.hateoas.mediatype.hal.json;

import com.srnjak.hateoas.mediatype.hal.HalCurie;
import com.srnjak.hateoas.mediatype.hal.HalCuries;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

import static com.srnjak.testing.json.AssertJson.assertContainsAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HalCuriesJsonTest {

    @Test
    public void convert_ToJson() {

        HalCurie halCurie1 = HalCurie.builder()
                .name("ts1")
                .href("http://www.example.com/1/{rel}")
                .templated(true)
                .build();

        HalCurie halCurie2 = HalCurie.builder()
                .name("ts2")
                .href("http://www.example.com/2/{rel}")
                .templated(true)
                .build();

        HalCurie halCurie3 = HalCurie.builder()
                .name("ts3")
                .href("http://www.example.com/3/{rel}")
                .templated(true)
                .build();

        HalCuries halCuries = HalCuries.builder()
                .add(halCurie1)
                .add(halCurie2)
                .add(halCurie3)
                .build();

        JsonObject expectedObj1 = Json.createObjectBuilder()
                .add("name", "ts1")
                .add("href", "http://www.example.com/1/{rel}")
                .add("templated", true)
                .build();

        JsonObject expectedObj2 = Json.createObjectBuilder()
                .add("name", "ts2")
                .add("href", "http://www.example.com/2/{rel}")
                .add("templated", true)
                .build();

        JsonObject expectedObj3 = Json.createObjectBuilder()
                .add("name", "ts3")
                .add("href", "http://www.example.com/3/{rel}")
                .add("templated", true)
                .build();

        JsonArray expected = Json.createArrayBuilder()
                .add(expectedObj1)
                .add(expectedObj2)
                .add(expectedObj3)
                .build();

        HalCuriesJson tut = new HalCuriesJson(halCuries);

        JsonArray actual = tut.toJsonArray();

        assertEquals(3, actual.size());
        assertContainsAll(expected, actual);
    }
}