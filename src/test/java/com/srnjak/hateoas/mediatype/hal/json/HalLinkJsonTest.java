package com.srnjak.hateoas.mediatype.hal.json;

import com.srnjak.hateoas.mediatype.hal.HalLink;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObject;

import static com.srnjak.testing.json.AssertJson.assertEquals;

class HalLinkJsonTest {

    @Test
    public void convert_ToJson() {

        HalLink halLink = HalLink.builder()
                .href("http://www.example.com/link")
                .templated(false)
                .type("text/plain")
                .deprecation("depr")
                .name("n")
                .profile("p")
                .title("t")
                .hreflang("en")
                .build();

        HalLinkJson tut = new HalLinkJson(halLink);

        JsonObject actual = tut.toJsonObject();

        JsonObject expected = Json.createObjectBuilder()
                .add("href", "http://www.example.com/link")
                .add("templated", false)
                .add("type", "text/plain")
                .add("deprecation", "depr")
                .add("name", "n")
                .add("profile", "p")
                .add("title", "t")
                .add("hreflang", "en")
                .build();

        assertEquals(expected, actual);
    }

}