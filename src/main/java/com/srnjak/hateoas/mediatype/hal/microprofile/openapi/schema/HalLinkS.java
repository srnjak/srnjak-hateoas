package com.srnjak.hateoas.mediatype.hal.microprofile.openapi.schema;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name="HalLink",
        example = "{\"href\": \"http://www.example.com/path/to/resource\"}")
public class HalLinkS {

    @Schema(required = true,
            example = "http://www.example.com/path/to/resource")
    public String href;

    public Boolean templated;

    @Schema(example = "application/hal+json")
    public String type;

    public Boolean deprecation;

    public String name;

    public String profile;

    @Schema(example = "My resource")
    public String title;

    @Schema(example = "en")
    public String hreflang;
}
