package com.srnjak.hateoas.mediatype.hal.json;

import com.srnjak.hateoas.relation.CurieDefinition;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObject;

import static com.srnjak.testing.json.AssertJson.assertEquals;

class HalCurieJsonTest {

    @Test
    public void convert_ToJson() {

        CurieDefinition curieDefinition = CurieDefinition.builder()
                .prefix("ts")
                .href("http://www.example.com/")
                .build();

        CurieDefinitionJson tut = new CurieDefinitionJson(curieDefinition);

        JsonObject actual = tut.toJsonObject();

        JsonObject expected = Json.createObjectBuilder()
                .add("name", "ts")
                .add("href", "http://www.example.com/{rel}")
                .add("templated", true)
                .build();

        assertEquals(expected, actual);
    }
}