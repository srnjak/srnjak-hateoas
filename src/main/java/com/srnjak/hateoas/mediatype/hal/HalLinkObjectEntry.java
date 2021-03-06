package com.srnjak.hateoas.mediatype.hal;

import com.srnjak.hateoas.LinkRelation;
import lombok.Value;

/**
 * An entry of a "links" object with a single link.
 */
@Value
public final class HalLinkObjectEntry implements HalLinkEntry {

    /**
     * The builder class.
     */
    public static class Builder {

        /**
         * The relation of the link.
         */
        private LinkRelation rel;

        /**
         * The link
         */
        private HalLink halLink;

        /**
         * Constructor.
         *
         * @param rel The relation of the link entry
         */
        public Builder rel(LinkRelation rel) {
            this.rel = rel;
            return this;
        }

        /**
         * Sets a link to the entry.
         *
         * @param halLink The link
         * @return This builder
         */
        public Builder setLink(HalLink halLink) {
            this.halLink = halLink;
            return this;
        }

        /**
         * Builds an {@link HalLinkObjectEntry} instance.
         *
         * @return The built {@link HalLinkObjectEntry} instance.
         */
        public HalLinkObjectEntry build() {
            return new HalLinkObjectEntry(this.rel, this.halLink);
        }
    }

    /**
     * @return The builder for this class
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * The relation of the links.
     */
    private final LinkRelation rel;

    /**
     * The link
     */
    private final HalLink halLink;
}
