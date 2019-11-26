package com.srnjak.hateoas;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The link relation which describes how the current context is related to the
 * target resource.
 */
@Data
@AllArgsConstructor
public class LinkRelation {

    /**
     * The value of link relation
     */
    private String value;

    /**
     * The type of link relation
     */
    private RelationType relationType;
}
