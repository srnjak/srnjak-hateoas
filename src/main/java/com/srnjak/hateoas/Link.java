package com.srnjak.hateoas;

import lombok.Builder;
import lombok.Data;

import java.net.URI;

@Data
@Builder
public class Link {

    private URI href;
    private Boolean templated;
    private String type;
    private String deprecation;
    private String name;
    private String profile;
    private String title;
    private String hreflang;
    private LinkRelation relation;
}
