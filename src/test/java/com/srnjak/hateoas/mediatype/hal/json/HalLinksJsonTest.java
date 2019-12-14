package com.srnjak.hateoas.mediatype.hal.json;

import com.srnjak.hateoas.mediatype.hal.*;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

import static com.srnjak.testing.json.AssertJson.assertContainsProperty;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HalLinksJsonTest {

    @Test
    public void convert_ToJson() {

        HalLink halLink1 = HalLink.builder()
                .href("http://www.example.com/1")
                .build();

        HalLink halLink2 = HalLink.builder()
                .href("http://www.example.com/2")
                .build();

        HalLink halLink3 = HalLink.builder()
                .href("http://www.example.com/3")
                .build();

        HalLinkEntry halLinkEntry1 =
                HalLinkObjectEntry.builder()
                        .rel("a")
                        .setLink(halLink1)
                        .build();
        HalLinkEntry halLinkEntry2 =
                HalLinkObjectEntry.builder()
                        .rel("b")
                        .setLink(halLink2)
                        .build();
        HalLinkEntry halLinkEntry3 =
                HalLinkObjectEntry.builder()
                        .rel("c")
                        .setLink(halLink3)
                        .build();

        HalLinkEntry halLinkEntry4 = HalLinkListEntry.builder()
                .rel("d")
                .addLink(halLink1)
                .addLink(halLink2)
                .addLink(halLink3)
                .build();

        HalLinks halLinks = HalLinks.builder()
                .add(halLinkEntry1)
                .add(halLinkEntry2)
                .add(halLinkEntry3)
                .add(halLinkEntry4)
                .build();

        JsonObject link1Obj = new HalLinkJson(halLink1).toJsonObject();
        JsonObject link2Obj = new HalLinkJson(halLink2).toJsonObject();
        JsonObject link3Obj = new HalLinkJson(halLink3).toJsonObject();

        JsonArray linksArray = Json.createArrayBuilder()
                .add(link1Obj)
                .add(link2Obj)
                .add(link3Obj)
                .build();

        HalLinksJson tut = new HalLinksJson(halLinks);

        JsonObject actual = tut.toJsonObject();

        assertEquals(4, actual.size());
        assertContainsProperty(link1Obj, "/a", actual);
        assertContainsProperty(link2Obj, "/b", actual);
        assertContainsProperty(link3Obj, "/c", actual);
        assertContainsProperty(linksArray, "/d", actual);
    }
}