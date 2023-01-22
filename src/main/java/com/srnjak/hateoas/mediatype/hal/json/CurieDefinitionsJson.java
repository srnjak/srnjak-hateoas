package com.srnjak.hateoas.mediatype.hal.json;

import com.srnjak.hateoas.relation.CurieDefinition;
import com.srnjak.hateoas.utils.JsonBuilderUtils;
import lombok.AllArgsConstructor;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import java.util.Set;

/**
 * A json serializer of a set of {@link CurieDefinition} objects.
 */
@AllArgsConstructor
public class CurieDefinitionsJson {

    /**
     * The hal representation of curies.
     */
    private Set<CurieDefinition> curieDefinitions;

    /**
     * Generates json object.
     *
     * @return The json object
     */
    public JsonArray toJsonArray() {
        return curieDefinitions.stream()
                .map(CurieDefinitionJson::new)
                .map(CurieDefinitionJson::toJsonObject)
                .map(Json::createObjectBuilder)
                .collect(Json::createArrayBuilder,
                        JsonBuilderUtils::add,
                        JsonBuilderUtils::add)
                .build();
    }
}
