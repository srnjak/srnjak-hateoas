package com.srnjak.hateoas.mediatype.hal.json;

import com.srnjak.hateoas.mediatype.hal.HalCurie;
import lombok.AllArgsConstructor;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.Optional;

/**
 * A json serializer of a {@link HalCurie} object.
 */
@AllArgsConstructor
public class HalCurieJson {

    /**
     * The hal representation of a curie.
     */
    private HalCurie halCurie;

    /**
     * Generates json object.
     *
     * @return The json object
     */
    public JsonObject toJsonObject() {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        Optional.ofNullable(halCurie.getName())
                .ifPresent(n -> builder.add(HalCurie.Fields.name, n));
        Optional.ofNullable(halCurie.getHref())
                .ifPresent(h -> builder.add(HalCurie.Fields.href, h));
        Optional.ofNullable(halCurie.getTemplated())
                .ifPresent(t -> builder.add(HalCurie.Fields.templated, t));

        return builder.build();
    }
}
