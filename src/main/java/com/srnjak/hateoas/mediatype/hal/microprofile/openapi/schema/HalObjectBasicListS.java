package com.srnjak.hateoas.mediatype.hal.microprofile.openapi.schema;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "HalObjectBasicList")
public class HalObjectBasicListS {

    public HalLinksBasicListS _links;
    public HalEmbeddedBasicListS _embedded;
}
