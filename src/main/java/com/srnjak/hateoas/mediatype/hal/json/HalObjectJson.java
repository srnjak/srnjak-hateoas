package com.srnjak.hateoas.mediatype.hal.json;

import com.srnjak.hateoas.mediatype.hal.*;
import com.srnjak.hateoas.relation.CurieDefinition;
import com.srnjak.hateoas.utils.GenericEntityWrapper;
import lombok.AllArgsConstructor;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.bind.JsonbBuilder;
import java.io.StringReader;
import java.util.Optional;
import java.util.Set;

/**
 * A json serializer of a {@link HalObject} object.
 */
@AllArgsConstructor
public class HalObjectJson {

    /**
     * The links property name.
     */
    public static final String LINKS = "_links";

    /**
     * The embedded property name.
     */
    public static final String EMBEDDED = "_embedded";

    /**
     * The hal representation.
     */
    private HalObject halObject;

    /**
     * Generates json object of HAL representation.
     *
     * @return The json object
     */
    public JsonObject toJsonObject() {
        CurieExtractor extractor = new CurieExtractor(this.halObject);
        Set<CurieDefinition> curieDefinitions = extractor.extract();

        return toJsonObjectContent(curieDefinitions);
    }

    JsonObject toEmbeddedJsonObject() {
        return toJsonObjectContent(null);
    }

    /**
     * Generates json object's content of HAL representation.
     *
     * @return The object's content in json format
     */
    private JsonObject toJsonObjectContent(
            Set<CurieDefinition> curieDefinitions) {

        Optional<JsonObject> links =
                Optional.ofNullable(halObject.getHalLinks())
                        .map(HalLinksJson::new)
                        .map(l -> l.toJsonObject(curieDefinitions));
        Optional<JsonObject> embedded =
                Optional.ofNullable(halObject.getEmbedded())
                        .map(HalEmbeddedJson::new)
                        .map(HalEmbeddedJson::toJsonObject);

        JsonObjectBuilder jsonObjectBuilder =
                Optional.ofNullable(halObject.getObject())
                        .map(this::toJson)
                        .map(j -> Json.createReader(new StringReader(j))
                                .readObject())
                        .map(o -> Json.createObjectBuilder(o)
                                .addAll(Json.createObjectBuilder(o)))
                        .orElseGet(Json::createObjectBuilder);

        links.ifPresent(l -> jsonObjectBuilder.add(LINKS, l));
        embedded.ifPresent(e -> jsonObjectBuilder.add(EMBEDDED, e));

        return jsonObjectBuilder.build();
    }

    /**
     * Serializes an object into json string representation.
     *
     * @param object The object to be serialized
     * @return The json string representation
     */
    private String toJson(Object object) {
        return Optional.of(object)
                .filter(o -> o instanceof GenericEntityWrapper)
                .map(o -> (GenericEntityWrapper) o)
                .map(o -> JsonbBuilder.create()
                        .toJson(o.getEntity(), o.getGenericType()))
                .orElseGet(() -> JsonbBuilder.create().toJson(object));
    }
}
