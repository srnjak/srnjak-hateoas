package com.srnjak.hateoas.mediatype.hal.microprofile.openapi.schema;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "HalLinksBasic")
public class HalLinksBasicS {
    public HalLinkS self;
}
