package com.srnjak.hateoas.mediatype.hal.json;

import com.srnjak.hateoas.mediatype.hal.HalCuries;
import com.srnjak.hateoas.utils.JsonBuilderUtils;
import lombok.AllArgsConstructor;

import javax.json.Json;
import javax.json.JsonArray;

/**
 * A json serializer of a {@link HalCuries} object.
 */
@AllArgsConstructor
public class HalCuriesJson {

    /**
     * The hal representation of curies.
     */
    private HalCuries halCuries;

    /**
     * Generates json object.
     *
     * @return The json object
     */
    public JsonArray toJsonArray() {
        return halCuries.getHalCurieSet().stream()
                .map(HalCurieJson::new)
                .map(HalCurieJson::toJsonObject)
                .map(Json::createObjectBuilder)
                .collect(Json::createArrayBuilder,
                        JsonBuilderUtils::add,
                        JsonBuilderUtils::add)
                .build();
    }
}
