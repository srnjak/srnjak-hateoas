package com.srnjak.hateoas.mediatype.hal;

import lombok.Value;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Holder for embedded objects in HAL representation.
 */
@Value
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
     * The set of embedded objects' entries
     */
    private Set<HalEmbeddedEntry> entrySet;
}
