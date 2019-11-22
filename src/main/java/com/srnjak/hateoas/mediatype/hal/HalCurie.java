package com.srnjak.hateoas.mediatype.hal;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.Optional;

@Value
@Builder
@FieldNameConstants
public class HalCurie {

    private String name;
    private String href;
    private Boolean templated;

    public JsonObject toJsonObject() {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        Optional.ofNullable(this.name)
                .ifPresent(n -> builder.add(Fields.name, n));
        Optional.ofNullable(this.href)
                .ifPresent(h -> builder.add(Fields.href, h));
        Optional.ofNullable(this.templated)
                .ifPresent(t -> builder.add(Fields.templated, t));

        return builder.build();
    }
}
