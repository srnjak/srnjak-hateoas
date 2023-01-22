package com.srnjak.hateoas.mediatype.hal.json;

import com.srnjak.hateoas.relation.CurieDefinition;
import org.junit.jupiter.api.Test;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import java.util.HashSet;
import java.util.Set;

import static com.srnjak.testing.json.AssertJson.assertContainsAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HalCuriesJsonTest {

    @Test
    public void convert_ToJson() {

        CurieDefinition curieDefinition1 = CurieDefinition.builder()
                .prefix("ts1")
                .href("http://www.example.com/1/")
                .build();

        CurieDefinition curieDefinition2 = CurieDefinition.builder()
                .prefix("ts2")
                .href("http://www.example.com/2/")
                .build();

        CurieDefinition curieDefinition3 = CurieDefinition.builder()
                .prefix("ts3")
                .href("http://www.example.com/3/")
                .build();

        Set<CurieDefinition> curieDefinitions = new HashSet<>();
        curieDefinitions.add(curieDefinition1);
        curieDefinitions.add(curieDefinition2);
        curieDefinitions.add(curieDefinition3);

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

        CurieDefinitionsJson tut = new CurieDefinitionsJson(curieDefinitions);

        JsonArray actual = tut.toJsonArray();

        assertEquals(3, actual.size());
        assertContainsAll(expected, actual);
    }
}