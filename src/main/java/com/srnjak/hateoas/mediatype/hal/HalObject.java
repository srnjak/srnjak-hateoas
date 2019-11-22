package com.srnjak.hateoas.mediatype.hal;

import lombok.NonNull;
import lombok.Value;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.bind.JsonbBuilder;
import java.io.StringReader;
import java.util.*;

@Value
public class HalObject {

    public static class Builder {

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

        public Builder addLink(@NonNull String rel, @NonNull HalLink halLink) {
            HalLinkEntry halLinkEntry =
                    HalLinkEntry.builder()
                            .rel(rel)
                            .addLink(halLink)
                            .buildSingle();

            this.halLinkEntrySet.add(halLinkEntry);
            return this;
        }

        public HalObject.Builder addLinks(HalLinkEntry halLinkEntry) {
            Optional.ofNullable(halLinkEntry)
                    .ifPresent(l -> this.halLinkEntrySet.add(l));
            return this;
        }

        public HalObject.Builder addLinks(
                @NonNull String rel,
                @NonNull List<HalLink> halLinks) {

            HalLinkEntry halLinkEntry = halLinks.stream()
                    .collect(HalLinkEntry.collector(rel));
            this.addLinks(halLinkEntry);
            return this;
        }

        public Builder addLinks(
                @NonNull String rel,
                @NonNull HalLink... halLinks) {

            this.addLinks(rel, Arrays.asList(halLinks));
            return this;
        }

        public Builder addLinks(@NonNull List<HalLinkEntry> halLinkEntryList) {
            halLinkEntryList.forEach(h -> this.addLinks(h));
            return this;
        }

        public Builder addCurie(HalCurie halCurie) {
            Optional.ofNullable(halCurie)
                    .ifPresent(c -> this.halCurieSet.add(c));
            return this;
        }

        public Builder addCuries(HalCuries halCuries) {

            Optional.ofNullable(halCuries)
                    .map(c -> c.getHalCurieSet())
                    .ifPresent(c -> this.halCurieSet.addAll(c));

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
                        .map(o -> Json.createObjectBuilder(o)
                                .addAll(Json.createObjectBuilder(o)))
                        .orElseGet(Json::createObjectBuilder);

        links.ifPresent(l -> jsonObjectBuilder.add("_links", l));
        embedded.ifPresent(e -> jsonObjectBuilder.add("_embedded", e));

        return jsonObjectBuilder.build();
    }

}
