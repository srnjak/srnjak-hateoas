package com.srnjak.hateoas;

import lombok.Data;

/**
 * Default relation type.
 *
 * It uses an URI as namespace.
 */
@Data
public class DefaultRelationType implements RelationType {

    /**
     * The URI
     */
    private String uri;

    /**
     * {@inheritDoc}
     */
    @Override
    public String prefix() {
        return this.uri;
    }
}
