package com.srnjak.hateoas.hal.schema;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

@Schema(name = "EmbeddedBasicList")
public class HalEmbeddedBasicListS {
    public List<HalObjectBasicS> item;
}
