package com.srnjak.hateoas;

import lombok.Data;

/**
 * Relation type of CURIE.
 *
 * @see <a href="https://www.w3.org/TR/2010/NOTE-curie-20101216/">
 *     CURIE Syntax 1.0</a>
 */
@Data
public class CurieRelationType implements RelationType {

    /**
     * The prefix
     */
    private String prefix;

    /**
     * The reference of the curie
     */
    private CurieReference reference;

    /**
     * {@inheritDoc}
     */
    @Override
    public String prefix() {
        return this.prefix;
    }
}
