package com.srnjak.hateoas.relation;

import com.srnjak.hateoas.LinkRelation;

import java.net.URI;

/**
 * A custom link relation.
 */
public interface CustomRelation extends LinkRelation {

    /**
     * @return The value of the link relation
     */
    default String value() {
        return getUri().toString();
    }

    /**
     * @return The {@link URI}
     */
    URI getUri();
}
