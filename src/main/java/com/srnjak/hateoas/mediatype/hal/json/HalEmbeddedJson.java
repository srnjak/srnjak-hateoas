package com.srnjak.hateoas.mediatype.hal.json;

import com.srnjak.hateoas.mediatype.hal.*;
import com.srnjak.hateoas.utils.JsonBuilderUtils;
import lombok.AllArgsConstructor;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A json serializer of a {@link HalEmbedded} object.
 */
@AllArgsConstructor
public class HalEmbeddedJson {

    /**
     * Adds an entry into json object builder.
     *
     * @param builder The json object builder
     * @param entry The entry
     */
    private static void addEntry(
            JsonObjectBuilder builder, HalEmbeddedEntry entry) {

        Map<Class<?>, Runnable> map = new HashMap<>();
        map.put(
                HalEmbeddedObjectEntry.class,
                () -> HalEmbeddedJson.addObject(
                        builder, (HalEmbeddedObjectEntry) entry));
        map.put(
                HalEmbeddedListEntry.class,
                () -> HalEmbeddedJson.addArray(
                        builder, (HalEmbeddedListEntry) entry));

        map.get(entry.getClass()).run();
    }

    /**
     * Adds an object into json object builder.
     *
     * @param builder The json object builder
     * @param entry The entry
     */
    private static void addObject(
            JsonObjectBuilder builder,
            HalEmbeddedObjectEntry entry) {

        JsonObject jsonObject = Optional.ofNullable(entry.getHalObject())
                .map(HalObjectJson::new)
                .map(HalObjectJson::toEmbeddedJsonObject)
                .get();

        builder.add(entry.getRel().getValue(), jsonObject);
    }

    /**
     * Adds an entry into json object builder as json array.
     *
     * @param builder The json object builder
     * @param entry The entry
     */
    private static void addArray(
            JsonObjectBuilder builder,
            HalEmbeddedListEntry entry) {

        JsonArray jsonArray = entry.getHalObjectList().stream()
                .map(HalObjectJson::new)
                .map(HalObjectJson::toEmbeddedJsonObject)
                .map(Json::createObjectBuilder)
                .collect(JsonBuilderUtils.collector())
                .build();

        builder.add(entry.getRel().getValue(), jsonArray);
    }

    /**
     * The hal representation of an embedded.
     */
    private HalEmbedded halEmbedded;

    /**
     * Generates json object.
     *
     * @return The json object
     */
    public JsonObject toJsonObject() {
        return halEmbedded.getEntrySet().stream()
                .collect(
                        Json::createObjectBuilder,
                        HalEmbeddedJson::addEntry,
                        JsonBuilderUtils::add)
                .build();
    }
}
