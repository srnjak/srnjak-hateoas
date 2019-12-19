package com.srnjak.hateoas.mediatype.hal.json;

import com.srnjak.hateoas.mediatype.hal.HalObject;
import com.srnjak.hateoas.utils.GenericEntityWrapper;
import lombok.AllArgsConstructor;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.bind.JsonbBuilder;
import java.io.StringReader;
import java.util.Optional;

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

    // TODO

    /**
     * Generates json object of HAL representation.
     *
     * @return The json object
     */
    public JsonObject toJsonObject() {

        Optional<JsonObject> links =
                Optional.ofNullable(halObject.getHalLinks())
                        .map(HalLinksJson::new)
                        .map(HalLinksJson::toJsonObject);
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

    private String toJson(Object object) {
        return Optional.of(object)
                .filter(o -> o instanceof GenericEntityWrapper)
                .map(o -> (GenericEntityWrapper) o)
                .map(o -> JsonbBuilder.create()
                        .toJson(o.getEntity(), o.getGenericType()))
                .orElseGet(() -> JsonbBuilder.create().toJson(object));
    }
}
