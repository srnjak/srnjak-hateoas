package com.srnjak.hateoas.utils;

import lombok.experimental.UtilityClass;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.util.stream.Collector;

/**
 * Utilities for building json.
 */
@UtilityClass
public class JsonBuilderUtils {

    /**
     * Creates a collector for collection {@link JsonObjectBuilder}
     * inside {@link JsonArrayBuilder}.
     *
     * @return The collector
     */
    public static Collector<
            JsonObjectBuilder,
            JsonArrayBuilder,
            JsonArrayBuilder> collector() {

        return Collector.of(
                Json::createArrayBuilder,
                JsonBuilderUtils::add,
                JsonBuilderUtils::add);
    }

    /**
     * Adds all json properties from one builder into another builder.
     *
     * @param objectBuilder The {@link JsonObjectBuilder} where properties
     *                      will be added to.
     * @param objectBuilder2 The {@link JsonObjectBuilder} where properties
     *                       will be added from.
     */
    public static void add(
            JsonObjectBuilder objectBuilder,
            JsonObjectBuilder objectBuilder2) {
        objectBuilder.addAll(objectBuilder2);
    }

    /**
     * Adds {@link JsonObjectBuilder} into {@link JsonArrayBuilder}.
     *
     * @param arrayBuilder The {@link JsonArrayBuilder}
     * @param objectBuilder The {@link JsonObjectBuilder}
     */
    public static void add(
            JsonArrayBuilder arrayBuilder,
            JsonObjectBuilder objectBuilder) {
        arrayBuilder.add(objectBuilder);
    }

    /**
     * Joins two {@link JsonArrayBuilder}s.
     *
     * @param arrayBuilder The {@link JsonArrayBuilder} where entries will
     *                     be added into.
     * @param arrayBuilder2 The {@link JsonArrayBuilder} where entries will be
     *                     added from.
     * @return The joined {@link JsonArrayBuilder};
     */
    public static JsonArrayBuilder add(
            JsonArrayBuilder arrayBuilder,
            JsonArrayBuilder arrayBuilder2) {

        return arrayBuilder.add(arrayBuilder2);
    }
}
