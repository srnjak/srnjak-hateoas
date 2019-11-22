package com.srnjak.hateoas.mediatype.hal.microprofile.openapi.schema;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

@Schema(name = "EmbeddedBasicList")
public class HalEmbeddedBasicListS {
    public List<HalObjectBasicS> item;
}
