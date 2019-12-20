package com.srnjak.hateoas.mediatype.hal;

import com.srnjak.hateoas.LinkRelation;
import lombok.NonNull;
import lombok.Value;

import java.util.*;

/**
 * Main container for HAL serialization.
 *
 * @see <a href="https://tools.ietf.org/html/draft-kelly-json-hal-08">
 *     JSON Hypertext Application Language</a>
 */
@Value
public class HalObject {

    /**
     * The builder class
     */
    @SuppressWarnings("WeakerAccess")
    public static class Builder {

        /**
         * The wrapped object
         */
        Object object;

        /**
         * The embedded objects
         */
        HalEmbedded embedded;

        /**
         * The set of link entries
         */
        Set<HalLinkEntry> halLinkEntrySet = new HashSet<>();

        /**
         * Constructor.
         */
        public Builder() {}

        /**
         * Constructor.
         *
         * @param object The object to be wrapped into HAL representation
         */
        public Builder(Object object) {
            this.object = object;
        }

        /**
         * Adds link to HAL representation.
         *
         * @param rel The link relation
         * @param halLink The link to be added
         * @return This builder
         */
        public Builder addLink(
                @NonNull LinkRelation rel, @NonNull HalLink halLink) {

            HalLinkEntry halLinkEntry =
                    HalLinkObjectEntry.builder()
                            .rel(rel)
                            .setLink(halLink)
                            .build();

            this.halLinkEntrySet.add(halLinkEntry);
            return this;
        }

        /**
         * Adds links to HAL representation.
         *
         * @param halLinkEntry The link entry
         * @return This builder
         */
        public HalObject.Builder addLinks(HalLinkEntry halLinkEntry) {
            Optional.ofNullable(halLinkEntry)
                    .ifPresent(l -> this.halLinkEntrySet.add(l));
            return this;
        }

        /**
         * Adds links to HAL representation.
         *
         * @param rel The link relation
         * @param halLinks The list of links to be added
         * @return This builder
         */
        public HalObject.Builder addLinks(
                @NonNull LinkRelation rel,
                @NonNull List<HalLink> halLinks) {

            HalLinkEntry halLinkEntry = halLinks.stream()
                    .collect(HalLinkListEntry.collector(rel));
            this.addLinks(halLinkEntry);
            return this;
        }

        /**
         * Adds links to HAL representation.
         *
         * @param rel The link relation
         * @param halLinks Links to be added
         * @return This builder
         */
        public Builder addLinks(
                @NonNull LinkRelation rel,
                @NonNull HalLink... halLinks) {

            this.addLinks(rel, Arrays.asList(halLinks));
            return this;
        }

        /**
         * Adds links to HAL representation.
         *
         * @param halLinkEntryList The list of link entries
         * @return This builder
         */
        public Builder addLinks(@NonNull List<HalLinkEntry> halLinkEntryList) {
            halLinkEntryList.forEach(this::addLinks);
            return this;
        }

        /**
         * Adds embedded object into HAL representation.
         *
         * @param halEmbedded The embedded object
         * @return This builder
         */
        public Builder addEmbedded(HalEmbedded halEmbedded) {
            this.embedded = halEmbedded;
            return this;
        }

        /**
         * Builds the {@link HalObject} instance.
         *
         * @return The built {@link HalObject} instance.
         */
        public HalObject build() {

            HalLinks halLinks = this.halLinkEntrySet.stream()
                    .collect(HalLinks.collector());

            return new HalObject(
                    this.object,
                    halLinks,
                    this.embedded);
        }
    }

    /**
     * @return The builder for this class
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * @param object The object to be wrapped into HAL
     * @return The builder for this class
     */
    public static Builder builder(Object object) {
        return new Builder(object);
    }

    /**
     * The wrapped object.
     */
    Object object;

    /**
     * The links related to the object representation.
     */
    HalLinks halLinks;

    /**
     * The embedded objects.
     */
    HalEmbedded embedded;

}
