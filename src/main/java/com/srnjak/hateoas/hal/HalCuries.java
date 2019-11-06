package com.srnjak.hateoas.hal;

import com.srnjak.hateoas.utils.JsonBuilderUtils;
import lombok.Value;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;

@Value
public class HalCuries {

    public static class Builder {
        private Set<HalCurie> halCurieSet = new HashSet<>();

        public Builder add(HalCurie halCurie) {
            this.halCurieSet.add(halCurie);
            return this;
        }

        public Builder addAll(Builder builder) {
            this.halCurieSet.addAll(builder.halCurieSet);
            return this;
        }

        public HalCuries build() {
            return Optional.of(this.halCurieSet)
                    .filter(c -> !c.isEmpty())
                    .map(c -> {
                        return new HalCuries(Collections.unmodifiableSet(
                                this.halCurieSet));
                    })
                    .orElse(null);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Collector<HalCurie, Builder, HalCuries> collector() {

        return Collector.of(
                HalCuries::builder,
                HalCuries::add,
                HalCuries::addAll,
                HalCuries.Builder::build
        );
    }

    static void add(Builder builder, HalCurie halCurie) {
        builder.add(halCurie);
    }

    static Builder addAll(Builder builder, Builder builder2) {
        return builder.addAll(builder2);
    }

    static void addCurieToJson(JsonArrayBuilder builder, HalCurie curie) {

        JsonObject jsonObject = Optional.of(curie)
                .map(c -> c.toJsonObject())
                .get();

        builder.add(jsonObject);
    }

    private Set<HalCurie> halCurieSet;

    public JsonArray toJsonArray() {
        return this.halCurieSet.stream()
                .map(HalCurie::toJsonObject)
                .map(Json::createObjectBuilder)
                .collect(Json::createArrayBuilder,
                        JsonBuilderUtils::add,
                        JsonBuilderUtils::add)
                .build();
    }
}
