package com.srnjak.hateoas.mediatype.hal;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;

/**
 * An entry of a "links" object with a list of links.
 */
@Getter
public final class HalLinkListEntry implements HalLinkEntry {

    /**
     * The builder class.
     */
    public static class Builder {

        /**
         * The relation of the links.
         */
        private String rel;

        /**
         * The list of links
         */
        private List<HalLink> halLinkList = new ArrayList<>();

        /**
         * Constructor.
         *
         * @param rel The relation of the link entry
         */
        public Builder rel(String rel) {
            this.rel = rel;
            return this;
        }

        /**
         * Adds a link to the entry.
         *
         * @param halLink The link
         * @return This builder
         */
        public Builder addLink(HalLink halLink) {
            this.halLinkList.add(halLink);
            return this;
        }

        /**
         * Adds links from another builder to the entry set.
         *
         * @param builder The another builder
         * @return This builder
         */
        public Builder addAll(Builder builder) {
            this.halLinkList.addAll(builder.halLinkList);
            return this;
        }

        /**
         * Builds an {@link HalLinkListEntry} instance with a list of links.
         *
         * @return The built {@link HalLinkListEntry} instance.
         */
        public HalLinkListEntry build() {
            return new HalLinkListEntry(this.rel, this.halLinkList);
        }
    }

    /**
     * @return The builder for this class
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Creates a collector for links to collect them into a links entry.
     *
     * @param rel The relation of the links entry
     * @return The collector
     */
    public static Collector<HalLink, Builder, HalLinkListEntry> collector(
            String rel) {

        return Collector.of(
                () -> HalLinkListEntry.builder().rel(rel),
                HalLinkListEntry::addLink,
                HalLinkListEntry::addAll,
                Builder::build
        );
    }

    /**
     * Adds link into a entry builder.
     *
     * @param builder The builder
     * @param halLink The link to be added
     */
    static void addLink(Builder builder, HalLink halLink) {
        builder.addLink(halLink);
    }

    /**
     * Adds links from one entry builder into another one.
     *
     * @param builder The builder where links will be added into
     * @param builder2 The builder from which links will be added from
     * @return The entry builder with added links
     */
    static Builder addAll(Builder builder, Builder builder2) {
        return builder.addAll(builder2);
    }

    /**
     * The relation of the links.
     */
    private final String rel;

    /**
     * The list of links
     */
    private final List<HalLink> halLinkList;

    /**
     * Constructor
     *
     * @param rel The relation
     * @param halLinkList The list of links
     */
    public HalLinkListEntry(String rel, List<HalLink> halLinkList) {
        this.rel = rel;
        this.halLinkList = Collections.unmodifiableList(halLinkList);
    }
}
