package com.srnjak.hateoas.hal.schema;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "HalObjectBasic")
public class HalObjectBasicS {
    public HalLinksBasicS _links;
}
