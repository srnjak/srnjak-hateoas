package com.srnjak.hateoas.mediatype.hal;

import lombok.NonNull;
import lombok.Value;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.bind.JsonbBuilder;
import java.io.StringReader;
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
     * The links property name.
     */
    public static final String LINKS = "_links";

    /**
     * The embedded property name.
     */
    public static final String EMBEDDED = "_embedded";

    /**
     * The builder class
     */
    @SuppressWarnings("WeakerAccess")
    public static class Builder {

        /**
         * The json object
         */
        JsonObject jsonObject;

        /**
         * The embedded objects
         */
        HalEmbedded embedded;

        /**
         * The set of link entries
         */
        Set<HalLinkEntry> halLinkEntrySet = new HashSet<>();

        /**
         * The set of curies
         */
        Set<HalCurie> halCurieSet = new HashSet<>();

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
            this(JsonbBuilder.create().toJson(object));
        }

        /**
         * Constructor.
         *
         * @param json The json string to be wrapped into HAL representation
         */
        public Builder(String json) {
            this.jsonObject =
                    Json.createReader(new StringReader(json)).readObject();
        }

        /**
         * Constructor.
         *
         * @param jsonObject The json object to be wrapped into
         *                   HAL representation
         */
        public Builder(JsonObject jsonObject) {
            this.jsonObject = jsonObject;
        }

        /**
         * Adds link to HAL representation.
         *
         * @param rel The link relation
         * @param halLink The link to be added
         * @return This builder
         */
        public Builder addLink(@NonNull String rel, @NonNull HalLink halLink) {
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
                @NonNull String rel,
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
                @NonNull String rel,
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
         * Adds a curie.
         *
         * @param halCurie The curie to be added
         * @return This builder
         */
        public Builder addCurie(HalCurie halCurie) {
            Optional.ofNullable(halCurie)
                    .ifPresent(c -> this.halCurieSet.add(c));
            return this;
        }

        /**
         * Adds curies.
         *
         * @param halCuries The curies to be added
         * @return This builder
         */
        public Builder addCuries(HalCuries halCuries) {

            Optional.ofNullable(halCuries)
                    .map(HalCuries::getHalCurieSet)
                    .ifPresent(c -> this.halCurieSet.addAll(c));

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

            HalCuries curies = this.halCurieSet.stream()
                    .collect(HalCuries.collector());

            HalLinks halLinks = this.halLinkEntrySet.stream()
                    .collect(HalLinks.collector(curies));

            return new HalObject(
                    this.jsonObject,
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
     * @param json Json to be wrapped into HAL
     * @return The builder for this class
     */
    public static Builder builder(String json) {
        return new Builder(json);
    }

    /**
     * @param jsonObject Json object to be wrapped into HAL
     * @return The builder for this class
     */
    public static Builder builder(JsonObject jsonObject) {
        return new Builder(jsonObject);
    }

    /**
     * The wrapped json object.
     */
    JsonObject jsonObject;

    /**
     * The links related to the object representation.
     */
    HalLinks halLinks;

    /**
     * The embedded objects.
     */
    HalEmbedded embedded;

    /**
     * Generates json object of HAL representation.
     *
     * @return The json object
     */
    public JsonObject toJsonObject() {

        Optional<JsonObject> links =
                Optional.ofNullable(this.halLinks).map(HalLinks::toJsonObject);
        Optional<JsonObject> embedded =
                Optional.ofNullable(this.embedded)
                        .map(HalEmbedded::toJsonObject);

        JsonObjectBuilder jsonObjectBuilder =
                Optional.ofNullable(this.jsonObject)
                        .map(o -> Json.createObjectBuilder(o)
                                .addAll(Json.createObjectBuilder(o)))
                        .orElseGet(Json::createObjectBuilder);

        links.ifPresent(l -> jsonObjectBuilder.add(LINKS, l));
        embedded.ifPresent(e -> jsonObjectBuilder.add(EMBEDDED, e));

        return jsonObjectBuilder.build();
    }

}
