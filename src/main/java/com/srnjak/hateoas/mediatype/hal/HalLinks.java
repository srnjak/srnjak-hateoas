package com.srnjak.hateoas.mediatype.hal;

import lombok.Value;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;

/**
 * Holder for link objects in HAL representation.
 */
@Value
public final class HalLinks {

    /**
     * The builder class
     */
    public static class Builder {

        /**
         * The set of link entries.
         */
        private Set<HalLinkEntry> entrySet = new HashSet<>();

        /**
         * The curies.
         */
        private HalCuries halCuries;

        /**
         * Adds an entry to the set.
         *
         * @param entry The entry
         * @return This builder
         */
        public Builder add(HalLinkEntry entry) {
            this.entrySet.add(entry);
            return this;
        }

        /**
         * Adds all entries to the set from another builder.
         *
         * @param builder The another builder
         * @return This builder
         */
        public Builder addAll(Builder builder) {
            this.entrySet.addAll(builder.entrySet);
            return this;
        }

        /**
         * Sets curies.
         *
         * @param halCuries The curies
         * @return This builder
         */
        public Builder curies(HalCuries halCuries) {
            this.halCuries = halCuries;
            return this;
        }

        /**
         * Builds the {@link HalLinks} instance.
         *
         * @return The built {@link HalLinks} instance.
         */
        public HalLinks build() {
            return Optional.of(this.entrySet)
                    .filter(e -> !e.isEmpty())
                    .map(e -> new HalLinks(
                            Collections.unmodifiableSet(this.entrySet),
                            this.halCuries))
                    .orElse(null);
        }
    }

    /**
     * @return The builder for this class
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Creates a collector for link entries to collect them into
     * an links entry.
     *
     * @param halCuries The curies included in the links property
     * @return The collector
     */
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

    /**
     * Creates a collector for link entries to collect them into
     * an links entry.
     *
     * @return The collector
     */
    public static Collector<HalLinkEntry, Builder, HalLinks> collector() {
        return collector(null);
    }

    /**
     * Adds an entry to a builder.
     *
     * @param builder The builder
     * @param halLinkEntry The entry
     */
    private static void addEntry(Builder builder, HalLinkEntry halLinkEntry) {
        builder.add(halLinkEntry);
    }

    /**
     * Adds entries from one builder into another one.
     *
     * @param builder The builder where entries will be added into
     * @param builder2 The builder from which entries will be added from
     * @return The builder with added entries
     */
    private static Builder addAll(Builder builder, Builder builder2) {
        return builder.addAll(builder2);
    }

    /**
     * The set of link entries.
     */
    private Set<HalLinkEntry> entrySet;

    /**
     * The curies.
     */
    private HalCuries curies;

}
