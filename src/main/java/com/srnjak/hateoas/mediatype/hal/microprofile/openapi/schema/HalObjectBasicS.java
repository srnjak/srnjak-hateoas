package com.srnjak.hateoas.mediatype.hal.microprofile.openapi.schema;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "HalObjectBasic")
public class HalObjectBasicS {
    public HalLinksBasicS _links;
}
