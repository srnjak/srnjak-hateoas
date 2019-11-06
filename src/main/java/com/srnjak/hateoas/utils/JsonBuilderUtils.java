package com.srnjak.hateoas.utils;

import lombok.experimental.UtilityClass;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.util.stream.Collector;

@UtilityClass
public class JsonBuilderUtils {

    public static Collector<
            JsonObjectBuilder,
            JsonArrayBuilder,
            JsonArrayBuilder>
    collector() {

        return Collector.of(
                Json::createArrayBuilder,
                JsonBuilderUtils::add,
                JsonBuilderUtils::add);
    }

    public static void add(
            JsonObjectBuilder objectBuilder,
            JsonObjectBuilder objectBuilder2) {
        objectBuilder.addAll(objectBuilder2);
    }

    public static void add(
            JsonArrayBuilder arrayBuilder,
            JsonObjectBuilder objectBuilder) {
        arrayBuilder.add(objectBuilder);
    }

    public static JsonArrayBuilder add(
            JsonArrayBuilder arrayBuilder,
            JsonArrayBuilder arrayBuilder2) {

        return arrayBuilder.add(arrayBuilder2);
    }
}
