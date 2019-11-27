package com.srnjak.hateoas.mediatype.hal;

import lombok.Value;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;

/**
 * Holder for curies objects in HAL representation.
 */
@Value
public class HalCuries {

    /**
     * The builder class
     */
    public static class Builder {

        /**
         * The set of curies.
         */
        private Set<HalCurie> halCurieSet = new HashSet<>();

        /**
         * Adds a curie to the set.
         *
         * @param halCurie The curie
         * @return This builder
         */
        public Builder add(HalCurie halCurie) {
            this.halCurieSet.add(halCurie);
            return this;
        }

        /**
         * Adds all curies to the set from another builder.
         *
         * @param builder The another builder
         * @return This builder
         */
        public Builder addAll(Builder builder) {
            this.halCurieSet.addAll(builder.halCurieSet);
            return this;
        }

        /**
         * Builds the {@link HalCuries} instance.
         *
         * @return The built {@link HalCuries} instance.
         */
        public HalCuries build() {
            return Optional.of(this.halCurieSet)
                    .filter(c -> !c.isEmpty())
                    .map(c -> new HalCuries(Collections.unmodifiableSet(
                            this.halCurieSet)))
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
     * Creates a collector for curies.
     *
     * @return The collector
     */
    public static Collector<HalCurie, Builder, HalCuries> collector() {

        return Collector.of(
                HalCuries::builder,
                HalCuries::add,
                HalCuries::addAll,
                HalCuries.Builder::build
        );
    }

    /**
     * Adds a curie to a builder.
     *
     * @param builder The builder
     * @param halCurie The curie
     */
    private static void add(Builder builder, HalCurie halCurie) {
        builder.add(halCurie);
    }

    /**
     * Adds curies from one builder into another one.
     *
     * @param builder The builder where curies will be added into
     * @param builder2 The builder from which curies will be added from
     * @return The builder with added curies
     */
    private static Builder addAll(Builder builder, Builder builder2) {
        return builder.addAll(builder2);
    }

    /**
     * The set of curies.
     */
    private Set<HalCurie> halCurieSet;
}
