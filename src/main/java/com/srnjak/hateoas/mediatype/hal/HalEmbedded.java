package com.srnjak.hateoas.mediatype.hal;

import com.srnjak.hateoas.utils.JsonBuilderUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.*;

/**
 * Holder for embedded objects in HAL representation.
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class HalEmbedded {

    /**
     * The builder class.
     */
    public static class Builder {

        /**
         * The set of embedded objects' entries
         */
        private Set<HalEmbeddedEntry> entrySet = new HashSet<>();

        /**
         * Adds new embedded object's entry into the set.
         *
         * @param entry The embedded object's entry
         * @return This builder
         */
        public Builder add(HalEmbeddedEntry entry) {
            this.entrySet.add(entry);
            return this;
        }

        /**
         * Builds the {@link HalEmbedded} instance.
         *
         * @return The built {@link HalEmbedded} instance.
         */
        public HalEmbedded build() {
            return new HalEmbedded(Collections.unmodifiableSet(this.entrySet));
        }
    }

    /**
     * @return The builder for this class
     */
    public static Builder builder() {
        return new Builder();
    }

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
                () -> HalEmbedded.addObject(
                        builder, (HalEmbeddedObjectEntry) entry));
        map.put(
                HalEmbeddedListEntry.class,
                () -> HalEmbedded.addArray(
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
                .map(HalObject::toJsonObject)
                .get();

        builder.add(entry.getRel(), jsonObject);
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
                .map(HalObject::toJsonObject)
                .map(Json::createObjectBuilder)
                .collect(JsonBuilderUtils.collector())
                .build();

        builder.add(entry.getRel(), jsonArray);
    }

    /**
     * The set of embedded objects' entries
     */
    private Set<HalEmbeddedEntry> entrySet;

    /**
     * Generates json object.
     *
     * @return The json object
     */
    public JsonObject toJsonObject() {
        return this.entrySet.stream()
                .collect(Json::createObjectBuilder,
                        HalEmbedded::addEntry,
                        JsonBuilderUtils::add)
                .build();
    }
}
