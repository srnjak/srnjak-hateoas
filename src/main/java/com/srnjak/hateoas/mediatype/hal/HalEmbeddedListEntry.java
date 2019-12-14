package com.srnjak.hateoas.mediatype.hal;

import lombok.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;

/**
 * List entry of an embedded object.
 */
@Value
public final class HalEmbeddedListEntry implements HalEmbeddedEntry {

    /**
     * The builder class.
     */
    public static class Builder {

        /**
         * The relation of the embedded entry.
         */
        private String rel;

        /**
         * The list of objects of the embedded entry.
         */
        private List<HalObject> halObjectList = new ArrayList<>();

        /**
         * Constructor.
         *
         * @param rel The relation of the embedded entry
         */
        public Builder(String rel) {
            this.rel = rel;
        }

        /**
         * Adds an object to the embedded entry.
         *
         * @param halObject The object to set
         * @return This builder
         */
        public Builder addObject(HalObject halObject) {
            this.halObjectList.add(halObject);
            return this;
        }

        /**
         * Adds objects from another builder to the embedded entry set.
         *
         * @param builder The another builder
         * @return This builder
         */
        public Builder addAll(Builder builder) {
            this.halObjectList.addAll(builder.halObjectList);
            return this;
        }

        /**
         * Builds an {@link HalEmbeddedListEntry} instance with a list
         * of objects.
         *
         * @return The built {@link HalEmbeddedListEntry} instance.
         */
        public HalEmbeddedListEntry build() {
            return new HalEmbeddedListEntry(this.rel, this.halObjectList);
        }
    }

    /**
     * @return The builder for this class
     */
    public static Builder builder(String name) {
        return new Builder(name);
    }

    /**
     * Creates a collector for hal objects to collect them into
     * an embedded entry.
     *
     * @param rel The relation of embedded entry
     * @return The collector
     */
    public static Collector<HalObject, Builder, HalEmbeddedListEntry> collector(
            String rel) {

        return Collector.of(
                () -> HalEmbeddedListEntry.builder(rel),
                HalEmbeddedListEntry::add,
                HalEmbeddedListEntry::addAll,
                Builder::build
        );
    }

    /**
     * Adds object into a entry builder.
     *
     * @param builder The builder
     * @param halObject The object to be added
     */
    private static void add(Builder builder, HalObject halObject) {
        builder.addObject(halObject);
    }

    /**
     * Adds objects from one entry builder into another one.
     *
     * @param builder The builder where objects will be added into
     * @param builder2 The builder from which objects will be added from
     * @return The entry builder with added objects
     */
    private static Builder addAll(Builder builder, Builder builder2) {
        return builder.addAll(builder2);
    }

    /**
     * The relation of the embedded entry
     */
    private final String rel;

    /**
     * The list of objects of the embedded entry
     */
    private final List<HalObject> halObjectList;

    /**
     * Constructor.
     *
     * @param rel The relation of the embedded entry
     * @param halObjectList The list of objects in the embedded entry
     */
    public HalEmbeddedListEntry(String rel, List<HalObject> halObjectList) {
        this.rel = rel;
        this.halObjectList = Collections.unmodifiableList(halObjectList);
    }
}
