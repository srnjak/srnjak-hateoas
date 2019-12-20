package com.srnjak.hateoas.mediatype.hal;

import com.srnjak.hateoas.LinkRelation;
import lombok.Value;

/**
 * Single entry of an embedded object.
 */
@Value
public final class HalEmbeddedObjectEntry implements HalEmbeddedEntry {

    /**
     * The builder class.
     */
    public static class Builder {

        /**
         * The relation of the embedded entry.
         */
        private LinkRelation rel;

        /**
         * The object of the embedded entry.
         */
        private HalObject halObject;

        /**
         * Constructor.
         *
         * @param rel The relation of the embedded entry
         */
        public Builder(LinkRelation rel) {
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
    public static Builder builder(LinkRelation rel) {
        return new Builder(rel);
    }

    /**
     * The relation of the embedded entry
     */
    private final LinkRelation rel;

    /**
     * The object of the embedded entry
     */
    private final HalObject halObject;
}
