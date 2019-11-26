package com.srnjak.hateoas.mediatype.hal;

import lombok.Getter;

/**
 * Single entry of an embedded object.
 */
@Getter
public final class HalEmbeddedObjectEntry implements HalEmbeddedEntry {

    /**
     * The builder class.
     */
    public static class Builder {

        /**
         * The relation of the embedded entry.
         */
        private String rel;

        /**
         * The object of the embedded entry.
         */
        private HalObject halObject;

        /**
         * Constructor.
         *
         * @param rel The relation of the embedded entry
         */
        public Builder(String rel) {
            this.rel = rel;
        }

        /**
         * Sets an object to the embedded entry.
         *
         * @param halObject The object to set
         * @return This builder
         */
        public Builder object(HalObject halObject) {
            this.halObject = halObject;
            return this;
        }

        /**
         * Builds an {@link HalEmbeddedObjectEntry} instance.
         *
         * @return The {@link HalEmbeddedObjectEntry} instance.
         */
        public HalEmbeddedObjectEntry build() {
            return new HalEmbeddedObjectEntry(this.rel, this.halObject);
        }
    }

    /**
     * @return The builder for this class
     */
    public static Builder builder(String name) {
        return new Builder(name);
    }

    /**
     * The relation of the embedded entry
     */
    private final String rel;

    /**
     * The object of the embedded entry
     */
    private final HalObject halObject;

    /**
     * Constructor.
     *
     * @param rel The relation of the embedded entry
     * @param halObject The object in the embedded entry
     */
    public HalEmbeddedObjectEntry(String rel, HalObject halObject) {
        this.rel = rel;
        this.halObject = halObject;
    }
}
