package com.srnjak.hateoas;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LinkRelation {

    private String value;
    private RelationType relationType;
}
