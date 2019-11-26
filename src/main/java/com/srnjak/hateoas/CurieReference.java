package com.srnjak.hateoas;

import lombok.Data;

/**
 * A target of a curie relation type.
 */
@Data
public class CurieReference {

    /**
     * The value where curie namespace is pointing to.
     */
    private String href;

    /**
     * Whether {@link CurieReference#href} is templated.
     */
    private Boolean templated;
}
