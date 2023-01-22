package com.srnjak.hateoas.mediatype.hal.json;

import com.srnjak.hateoas.mediatype.hal.*;
import com.srnjak.hateoas.relation.IanaLinkRelation;
import lombok.Data;
import org.junit.jupiter.api.Test;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

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
                HalEmbeddedObjectEntry.builder(IanaLinkRelation.SELF)
                        .object(halEmbedded1)
                        .build();

        HalEmbeddedEntry halEmbeddedEntry2 =
                HalEmbeddedObjectEntry.builder(IanaLinkRelation.PREV)
                        .object(halEmbedded2)
                        .build();

        HalEmbeddedEntry halEmbeddedEntry3 =
                HalEmbeddedObjectEntry.builder(IanaLinkRelation.NEXT)
                        .object(halEmbedded3)
                        .build();

        HalEmbeddedEntry halEmbeddedEntry4 =
                HalEmbeddedListEntry.builder(IanaLinkRelation.ITEM)
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
        assertContainsProperty(
                obj1, "/" + IanaLinkRelation.SELF.getValue(), actual);
        assertContainsProperty(
                obj2, "/" + IanaLinkRelation.PREV.getValue(), actual);
        assertContainsProperty(
                obj3, "/" + IanaLinkRelation.NEXT.getValue(), actual);
        assertContainsProperty(
                array, "/" + IanaLinkRelation.ITEM.getValue(), actual);

    }

}