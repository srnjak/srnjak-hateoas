package com.srnjak.hateoas;

/**
 * The link relation which describes how the current context is related to the
 * target resource.
 */
public interface LinkRelation {

    /**
     * The value of link relation
     *
     * @return The value of the link relation
     */
    String getValue();
}
