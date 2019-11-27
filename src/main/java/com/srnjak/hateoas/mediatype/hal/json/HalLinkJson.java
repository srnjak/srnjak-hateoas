package com.srnjak.hateoas.mediatype.hal.json;

import com.srnjak.hateoas.mediatype.hal.HalLink;
import lombok.AllArgsConstructor;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.Optional;

/**
 * A json serializer of a {@link HalLink} object.
 */
@AllArgsConstructor
public class HalLinkJson {

    /**
     * The hal representation of a link.
     */
    private HalLink halLink;

    /**
     * Generates json object.
     *
     * @return The json object
     */
    public JsonObject toJsonObject() {

        JsonObjectBuilder builder = Json.createObjectBuilder();

        Optional.ofNullable(halLink.getHref())
                .ifPresent(h -> builder.add(HalLink.Fields.href, h));
        Optional.ofNullable(halLink.getTemplated())
                .ifPresent(t -> builder.add(HalLink.Fields.templated, t));
        Optional.ofNullable(halLink.getType())
                .ifPresent(t -> builder.add(HalLink.Fields.type, t));
        Optional.ofNullable(halLink.getDeprecation())
                .ifPresent(d -> builder.add(HalLink.Fields.deprecation, d));
        Optional.ofNullable(halLink.getName())
                .ifPresent(n -> builder.add(HalLink.Fields.name, n));
        Optional.ofNullable(halLink.getProfile())
                .ifPresent(p -> builder.add(HalLink.Fields.profile, p));
        Optional.ofNullable(halLink.getTitle())
                .ifPresent(t -> builder.add(HalLink.Fields.title, t));
        Optional.ofNullable(halLink.getHreflang())
                .ifPresent(h -> builder.add(HalLink.Fields.hreflang, h));

        return builder.build();
    }
}
