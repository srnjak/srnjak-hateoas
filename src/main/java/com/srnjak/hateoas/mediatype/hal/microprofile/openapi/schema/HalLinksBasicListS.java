package com.srnjak.hateoas.mediatype.hal.microprofile.openapi.schema;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

@Schema(name = "HalLinksBasicList")
public class HalLinksBasicListS {
    public HalLinkS self;
    public List<HalLinkS> item;
}