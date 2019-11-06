package com.srnjak.hateoas.hal;

import com.srnjak.hateoas.utils.JsonBuilderUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class HalEmbedded {

    public static class Builder {
        private Set<HalEmbeddedEntry> entrySet = new HashSet<>();

        public Builder put(HalEmbeddedEntry entry) {
            this.entrySet.add(entry);
            return this;
        }

        public HalEmbedded build() {
            return new HalEmbedded(Collections.unmodifiableSet(this.entrySet));
        }
    }

    private Set<HalEmbeddedEntry> entrySet;

    static void addEntry(
            JsonObjectBuilder jsonObjectBuilder, HalEmbeddedEntry entry) {
        Optional.ofNullable(entry.getHalObject())
                .ifPresentOrElse(
                        e -> HalEmbedded.addObject(jsonObjectBuilder, entry),
                        () -> HalEmbedded.addArray(jsonObjectBuilder, entry));
    }


    static void addObject(JsonObjectBuilder builder, HalEmbeddedEntry entry) {
        JsonObject jsonObject = Optional.ofNullable(entry.getHalObject())
                .map(o -> o.toJsonObject())
                .get();

        builder.add(entry.getName(), jsonObject);
    }

    static void addArray(JsonObjectBuilder builder, HalEmbeddedEntry entry) {
        JsonArray jsonArray = entry.getHalObjectList().stream()
                .map(o -> o.toJsonObject())
                .map(Json::createObjectBuilder)
                .collect(JsonBuilderUtils.collector())
                .build();

        builder.add(entry.getName(), jsonArray);
    }

    public JsonObject toJsonObject() {
        return this.entrySet.stream()
                .collect(Json::createObjectBuilder,
                        HalEmbedded::addEntry,
                        JsonBuilderUtils::add)
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }
}
