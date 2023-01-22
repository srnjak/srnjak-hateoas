package com.srnjak.hateoas.mediatype.hal.json;

import com.srnjak.hateoas.mediatype.hal.*;
import com.srnjak.hateoas.relation.CurieDefinition;
import com.srnjak.hateoas.utils.JsonBuilderUtils;
import lombok.AllArgsConstructor;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * A json serializer of a {@link HalLinks} object.
 */
@AllArgsConstructor
public class HalLinksJson {

    /**
     * The curies json property name
     */
    public static final String CURIES = "curies";

    /**
     * Adds entry to json builder.
     *
     * @param builder The json builder
     * @param entry The entry
     */
    private static void addEntryToJson(
            JsonObjectBuilder builder, HalLinkEntry entry) {

        Map<Class<?>, Runnable> map = new HashMap<>();
        map.put(HalLinkObjectEntry.class, () -> {
            HalLinkObjectEntry objectEntry = (HalLinkObjectEntry) entry;

            JsonObject jsonObject = Optional.of(objectEntry)
                    .map(HalLinkObjectEntry::getHalLink)
                    .map(HalLinkJson::new)
                    .map(HalLinkJson::toJsonObject)
                    .get();

            builder.add(objectEntry.getRel().getValue(), jsonObject);
        });

        map.put(HalLinkListEntry.class, () -> {
            HalLinkListEntry listEntry = (HalLinkListEntry) entry;

            JsonArrayBuilder jsonArrayBuilder = listEntry.getHalLinkList()
                    .stream()
                    .map(HalLinkJson::new)
                    .map(HalLinkJson::toJsonObject)
                    .map(Json::createObjectBuilder)
                    .collect(JsonBuilderUtils.collector());

            builder.add(listEntry.getRel().getValue(), jsonArrayBuilder);
        });

        map.get(entry.getClass()).run();
    }

    /**
     * The hal representation of links.
     */
    private HalLinks halLinks;

    /**
     * Generates json object.
     *
     * @return The json object
     */
    public JsonObject toJsonObject() {
        return toJsonObject(null);
    }

    /**
     * Generates json object.
     *
     * @param curieDefinitions The set of {@link CurieDefinition} objects
     * @return The json object
     */
    public JsonObject toJsonObject(Set<CurieDefinition> curieDefinitions) {

        JsonObjectBuilder jsonObjectBuilder = halLinks.getEntrySet().stream()
                .collect(
                        Json::createObjectBuilder,
                        HalLinksJson::addEntryToJson,
                        JsonBuilderUtils::add);

        Optional.ofNullable(curieDefinitions)
                .filter(c -> !c.isEmpty())
                .map(CurieDefinitionsJson::new)
                .map(CurieDefinitionsJson::toJsonArray)
                .ifPresent(c -> jsonObjectBuilder.add(CURIES, c));

        return jsonObjectBuilder.build();
    }
}
