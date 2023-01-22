package com.srnjak.hateoas.mediatype.hal.json;

import com.srnjak.hateoas.relation.CurieDefinition;
import lombok.AllArgsConstructor;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

/**
 * A json serializer of a {@link CurieDefinition} object.
 */
@AllArgsConstructor
public class CurieDefinitionJson {

    /**
     * Name property
     */
    public static final String NAME = "name";

    /**
     * Href property
     */
    public static final String HREF = "href";

    /**
     * Templated property
     */
    public static final String TEMPLATED = "templated";

    /**
     * "rel" placeholder.
     */
    public static final String REL_PLACEHOLDER = "{rel}";

    /**
     * The hal representation of a curie.
     */
    private CurieDefinition curieDefinition;

    /**
     * Generates json object.
     *
     * @return The json object
     */
    public JsonObject toJsonObject() {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add(NAME, curieDefinition.getPrefix());
        builder.add(HREF, curieDefinition.getHref() + REL_PLACEHOLDER);
        builder.add(TEMPLATED, true);

        return builder.build();
    }
}
