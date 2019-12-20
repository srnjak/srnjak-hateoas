package com.srnjak.hateoas.relation;

import java.net.URI;
import java.util.Optional;

/**
 * Curie link relation.
 */
public interface CurieLinkRelation extends CustomRelation {

    /**
     * @return The value of the link relation
     */
    default String getValue() {
        return getCurie().getPrefix() + ":" + getReference();
    }

    /**
     * Calculates an {@link URI}.
     *
     * @return The {@link URI}
     */
    default URI getUri() {
        return Optional.of(getReference())
                .map(r -> getCurie().getHref() + r)
                .map(URI::create)
                .get();
    }

    /**
     * @return The curie definition
     */
    CurieDefinition getCurie();

    /**
     * @return The reference of the relation
     */
    String getReference();
}
