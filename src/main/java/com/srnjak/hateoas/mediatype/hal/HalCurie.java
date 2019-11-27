package com.srnjak.hateoas.mediatype.hal;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

/**
 * A curie representation.
 *
 * @see <a href="https://tools.ietf.org/html/draft-kelly-json-hal-08#section-8.2">
 *     Hal specification - Link relations</a>
 * @see <a href="https://www.w3.org/TR/2010/NOTE-curie-20101216/">
 *     CURIE Syntax 1.0</a>
 */
@Value
@Builder
@FieldNameConstants
public class HalCurie {

    /**
     * The name of the curie, which is used as a prefix in relations.
     */
    private String name;

    /**
     * The path to the documentation of relation, specified by this curie.
     */
    private String href;

    /**
     * Whether href attribute is url template.
     */
    private Boolean templated;
}
