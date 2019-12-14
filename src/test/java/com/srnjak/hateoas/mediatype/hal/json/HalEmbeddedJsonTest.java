package com.srnjak.hateoas.mediatype.hal.json;

import com.srnjak.hateoas.mediatype.hal.*;
import lombok.Data;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

import static com.srnjak.testing.json.AssertJson.assertContainsProperty;
import static org.junit.jupiter.api.Assertions.*;

class HalEmbeddedJsonTest {

    @Data
    public static class TestEntity {
        private String a;
        private Integer b;
        private boolean c;
    }

    @Test
    public void convert_ToJson() {

        TestEntity entity1 = new TestEntity();
        entity1.setA("aaa");
        entity1.setB(42);
        entity1.setC(true);

        TestEntity entity2 = new TestEntity();
        entity2.setA(null);
        entity2.setB(0);
        entity2.setC(false);

        HalObject halEmbedded1 = HalObject.builder(entity1).build();
        HalObject halEmbedded2 = HalObject.builder(entity2).build();
        HalObject halEmbedded3 = HalObject.builder().build();

        HalEmbeddedEntry halEmbeddedEntry1 =
                HalEmbeddedObjectEntry.builder("a")
                        .object(halEmbedded1)
                        .build();

        HalEmbeddedEntry halEmbeddedEntry2 =
                HalEmbeddedObjectEntry.builder("b")
                        .object(halEmbedded2)
                        .build();

        HalEmbeddedEntry halEmbeddedEntry3 =
                HalEmbeddedObjectEntry.builder("c")
                        .object(halEmbedded3)
                        .build();

        HalEmbeddedEntry halEmbeddedEntry4 =
                HalEmbeddedListEntry.builder("d")
                        .addObject(halEmbedded1)
                        .addObject(halEmbedded2)
                        .addObject(halEmbedded3)
                        .build();

        HalEmbedded halEmbedded = HalEmbedded.builder()
                .add(halEmbeddedEntry1)
                .add(halEmbeddedEntry2)
                .add(halEmbeddedEntry3)
                .add(halEmbeddedEntry4)
                .build();

        JsonObject obj1 = new HalObjectJson(halEmbedded1).toJsonObject();
        JsonObject obj2 = new HalObjectJson(halEmbedded2).toJsonObject();
        JsonObject obj3 = new HalObjectJson(halEmbedded3).toJsonObject();

        JsonArray array = Json.createArrayBuilder()
                .add(obj1)
                .add(obj2)
                .add(obj3)
                .build();

        HalEmbeddedJson tut = new HalEmbeddedJson(halEmbedded);

        JsonObject actual = tut.toJsonObject();
        System.out.println(actual);

        assertEquals(4, actual.size());
        assertContainsProperty(obj1, "/a", actual);
        assertContainsProperty(obj2, "/b", actual);
        assertContainsProperty(obj3, "/c", actual);
        assertContainsProperty(array, "/d", actual);

    }

}