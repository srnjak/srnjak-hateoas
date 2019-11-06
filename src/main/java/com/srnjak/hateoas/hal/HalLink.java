package com.srnjak.hateoas.hal;

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
public class HalLink {
    private String href;
    private Boolean templated;
    private String type;
    private String deprecation;
    private String name;
    private String profile;
    private String title;
    private String hreflang;

    public JsonObject toJsonObject() {

        JsonObjectBuilder builder = Json.createObjectBuilder();

        Optional.ofNullable(this.href)
                .ifPresent(h -> builder.add(Fields.href, h));
        Optional.ofNullable(this.templated)
                .ifPresent(t -> builder.add(Fields.templated, t));
        Optional.ofNullable(this.type)
                .ifPresent(t -> builder.add(Fields.type, t));
        Optional.ofNullable(this.deprecation)
                .ifPresent(d -> builder.add(Fields.deprecation, d));
        Optional.ofNullable(this.name)
                .ifPresent(n -> builder.add(Fields.name, n));
        Optional.ofNullable(this.profile)
                .ifPresent(p -> builder.add(Fields.profile, p));
        Optional.ofNullable(this.title)
                .ifPresent(t -> builder.add(Fields.title, t));
        Optional.ofNullable(this.hreflang)
                .ifPresent(h -> builder.add(Fields.hreflang, h));

        return builder.build();
    }


}
