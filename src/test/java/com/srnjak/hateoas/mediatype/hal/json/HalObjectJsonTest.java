package com.srnjak.hateoas.mediatype.hal.json;

import com.srnjak.hateoas.mediatype.hal.*;
import com.srnjak.hateoas.relation.IanaLinkRelation;
import lombok.Data;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;

import static com.srnjak.testing.json.AssertJson.assertContainsProperty;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HalObjectJsonTest {

    @Data
    public static class TestEntity {
        private String a;
        private Integer b;
        private boolean c;
    }

    @Test
    public void convert_ToJson() {

        HalLink halLink =
                HalLink.builder().href("http://www.example.com/").build();

        TestEntity entity = new TestEntity();
        entity.setA("aaa");
        entity.setB(12);
        entity.setC(true);

        HalObject halObject = HalObject.builder(entity)
                .addLink(IanaLinkRelation.SELF, halLink)
                .addLinks(IanaLinkRelation.COLLECTION, halLink)
                .build();

        JsonObject links =
                new HalLinksJson(halObject.getHalLinks()).toJsonObject();

        HalObjectJson tut = new HalObjectJson(halObject);

        JsonObject actual = tut.toJsonObject();

        assertEquals(4, actual.size());
        assertContainsProperty(
                Json.createValue("aaa"), "/a", actual);
        assertContainsProperty(Json.createValue(12), "/b", actual);
        assertContainsProperty(JsonValue.TRUE, "/c", actual);
        assertContainsProperty(links, "/_links", actual);
    }

    @Test
    public void convert_ToJson_WithEmbedded() {

        HalLink halLink =
                HalLink.builder().href("http://www.example.com/").build();

        TestEntity entity = new TestEntity();
        entity.setA("aaa");
        entity.setB(12);
        entity.setC(true);

        HalObject embedded = HalObject.builder(entity)
                .addLink(IanaLinkRelation.SELF, halLink)
                .addLinks(IanaLinkRelation.ABOUT, halLink)
                .build();

        HalEmbeddedEntry embeddedEntry = HalEmbeddedObjectEntry
                .builder(IanaLinkRelation.APPENDIX)
                .object(embedded)
                .build();

        HalEmbedded halEmbedded =
                HalEmbedded.builder().add(embeddedEntry).build();

        HalObject halObject = HalObject.builder(entity)
                .addLink(IanaLinkRelation.SELF, halLink)
                .addLinks(IanaLinkRelation.COLLECTION, halLink)
                .addEmbedded(halEmbedded)
                .build();

        JsonObject linksJson =
                new HalLinksJson(halObject.getHalLinks()).toJsonObject();
        JsonObject embJson = new HalEmbeddedJson(halEmbedded).toJsonObject();

        HalObjectJson tut = new HalObjectJson(halObject);

        JsonObject actual = tut.toJsonObject();

        assertEquals(5, actual.size());
        assertContainsProperty(
                Json.createValue("aaa"), "/a", actual);
        assertContainsProperty(Json.createValue(12), "/b", actual);
        assertContainsProperty(JsonValue.TRUE, "/c", actual);
        assertContainsProperty(linksJson, "/_links", actual);
        assertContainsProperty(embJson, "/_embedded", actual);
    }

}