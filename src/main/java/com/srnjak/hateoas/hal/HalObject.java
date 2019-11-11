package com.srnjak.hateoas.hal;

import lombok.Value;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;

@Value
public class HalObject {

    public static class Builder {

        static HalLink toHalLink(Link link) {
            return HalLink.builder()
                    .href(link.getUri().toString())
                    .title(link.getTitle())
                    .type(link.getType())
                    .build();
        }

        JsonObject jsonObject;
        HalEmbedded embedded;
        Set<HalLinkEntry> halLinkEntrySet = new HashSet<>();
        Set<HalCurie> halCurieSet = new HashSet<>();

        public Builder() {}

        public Builder(Object object) {
            this(JsonbBuilder.create().toJson(object));
        }

        public Builder(String json) {
            this.jsonObject =
                    Json.createReader(new StringReader(json)).readObject();
        }

        public Builder(JsonObject jsonObject) {
            this.jsonObject = jsonObject;
        }

        public Builder addLink(Link link) {

            HalLink halLink = toHalLink(link);

            HalLinkEntry halLinkEntry =
                    HalLinkEntry.builder()
                            .rel(link.getRel())
                            .addLink(halLink)
                            .buildSingle();

            this.halLinkEntrySet.add(halLinkEntry);
            return this;
        }

        public Builder addLinks(Link... links) {

            Map<String, List<Link>> linksPerRel = Arrays.stream(links)
                    .collect(Collectors.groupingBy(Link::getRel));

            linksPerRel.keySet().forEach(r -> {
                HalLinkEntry halLinkEntry = linksPerRel.get(r).stream()
                        .map(Builder::toHalLink)
                        .collect(HalLinkEntry.collector(r));

                this.halLinkEntrySet.add(halLinkEntry);
            });

            return this;
        }

        public Builder addLink(String rel, HalLink halLink) {
            HalLinkEntry halLinkEntry =
                    HalLinkEntry.builder()
                            .rel(rel)
                            .addLink(halLink)
                            .buildSingle();

            this.halLinkEntrySet.add(halLinkEntry);
            return this;
        }

        public HalObject.Builder addLinks(HalLinkEntry halLinkEntry) {
            this.halLinkEntrySet.add(halLinkEntry);
            return this;
        }

        public HalObject.Builder addLinks(String rel, List<HalLink> halLinks) {
            HalLinkEntry halLinkEntry = halLinks.stream()
                    .collect(HalLinkEntry.collector(rel));
            this.addLinks(halLinkEntry);
            return this;
        }

        public Builder addLinks(String rel, HalLink... halLinks) {
            this.addLinks(rel, Arrays.asList(halLinks));
            return this;
        }

        public Builder addCurie(HalCurie halCurie) {
            this.halCurieSet.add(halCurie);
            return this;
        }

        public Builder addCuries(HalCuries halCuries) {
            this.halCurieSet.addAll(halCuries.getHalCurieSet());
            return this;
        }

        public Builder addEmbedded(HalEmbedded halEmbedded) {
            this.embedded = halEmbedded;
            return this;
        }

        public HalObject build() {

            HalCuries curies = this.halCurieSet.stream()
                    .collect(HalCuries.collector());

            HalLinks halLinks = this.halLinkEntrySet.stream()
                    .collect(HalLinks.collector(curies));

            return new HalObject(
                    this.jsonObject,
                    halLinks,
                    this.embedded);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Object object) {
        return new Builder(object);
    }

    public static Builder builder(String json) {
        return new Builder(json);
    }

    public static Builder builder(JsonObject jsonObject) {
        return new Builder(jsonObject);
    }

    JsonObject jsonObject;
    HalLinks halLinks;
    HalEmbedded embedded;

    public JsonObject toJsonObject() {

        Optional<JsonObject> links =
                Optional.ofNullable(this.halLinks).map(HalLinks::toJsonObject);
        Optional<JsonObject> embedded =
                Optional.ofNullable(this.embedded)
                        .map(HalEmbedded::toJsonObject);

        JsonObjectBuilder jsonObjectBuilder =
                Optional.ofNullable(this.jsonObject)
                        .map(o -> {
                            return Json.createObjectBuilder(o)
                                    .addAll(Json.createObjectBuilder(o));
                        })
                        .orElseGet(Json::createObjectBuilder);

        links.ifPresent(l -> jsonObjectBuilder.add("_links", l));
        embedded.ifPresent(e -> jsonObjectBuilder.add("_embedded", e));

        return jsonObjectBuilder.build();
    }

}
