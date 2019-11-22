package com.srnjak.hateoas.mediatype.hal;

import com.srnjak.hateoas.utils.JsonBuilderUtils;

import javax.json.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;

public final class HalLinks {

    public static final String CURIES = "curies";

    public static Collector<HalLinkEntry, Builder, HalLinks> collector(
            HalCuries halCuries) {

        Builder builder = Optional.ofNullable(halCuries)
                .filter(c -> !c.getHalCurieSet().isEmpty())
                .map(c -> HalLinks.builder().curies(c))
                .orElse(HalLinks.builder());

        return Collector.of(
                () -> builder,
                HalLinks::addEntry,
                HalLinks::addAll,
                HalLinks.Builder::build);
    }

    static void addEntryToJson(JsonObjectBuilder builder, HalLinkEntry entry) {
        Optional<JsonObject> jsonObjectOptional =
                Optional.ofNullable(entry.getHalLink())
                        .map(HalLink::toJsonObject);

        jsonObjectOptional.ifPresentOrElse(
                o -> builder.add(entry.getRel(), o),
                () -> {
                    JsonArrayBuilder jsonArrayBuilder = entry.getHalLinkList()
                            .stream()
                            .map(HalLink::toJsonObject)
                            .map(Json::createObjectBuilder)
                            .collect(JsonBuilderUtils.collector());

                    builder.add(entry.getRel(), jsonArrayBuilder);
                });
    }

    static void addEntry(Builder builder, HalLinkEntry halLinkEntry) {
        builder.add(halLinkEntry);
    }

    static Builder addAll(Builder builder, Builder builder2) {
        return builder.addAll(builder2);
    }

    private Set<HalLinkEntry> entrySet;
    private HalCuries curies;

    private HalLinks(Set<HalLinkEntry> entrySet, HalCuries curies) {
        this.entrySet = entrySet;
        this.curies = curies;
    }

    public JsonObject toJsonObject() {

        JsonObjectBuilder jsonObjectBuilder = this.entrySet.stream()
                .collect(Json::createObjectBuilder,
                        HalLinks::addEntryToJson,
                        JsonBuilderUtils::add);

        Optional.ofNullable(this.curies)
                .ifPresent(c -> {
                    jsonObjectBuilder.add(CURIES, curies.toJsonArray());
                });

        return jsonObjectBuilder.build();
    }

    // Builder
    public static class Builder {
        private Set<HalLinkEntry> entrySet = new HashSet<>();
        private HalCuries halCuries;

        public Builder add(HalLinkEntry entry) {
            this.entrySet.add(entry);
            return this;
        }

        public Builder addAll(Builder builder) {
            this.entrySet.addAll(builder.entrySet);
            return this;
        }

        public Builder curies(HalCuries halCuries) {
            this.halCuries = halCuries;
            return this;
        }

        public HalLinks build() {
            return Optional.of(this.entrySet)
                    .filter(e -> !e.isEmpty())
                    .map(e -> {
                        return new HalLinks(
                                Collections.unmodifiableSet(this.entrySet),
                                this.halCuries);
                    })
                    .orElse(null);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
