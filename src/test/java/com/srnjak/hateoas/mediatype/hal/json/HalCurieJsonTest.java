package com.srnjak.hateoas.mediatype.hal.json;

import com.srnjak.hateoas.mediatype.hal.HalCurie;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObject;

import static com.srnjak.testing.json.AssertJson.assertEquals;

class HalCurieJsonTest {

    @Test
    public void convert_ToJson() {

        HalCurie halCurie = HalCurie.builder()
                .name("ts")
                .href("http://www.example.com/{rel}")
                .templated(true)
                .build();

        HalCurieJson tut = new HalCurieJson(halCurie);

        JsonObject actual = tut.toJsonObject();

        JsonObject expected = Json.createObjectBuilder()
                .add("name", "ts")
                .add("href", "http://www.example.com/{rel}")
                .add("templated", true)
                .build();

        assertEquals(expected, actual);
    }
}