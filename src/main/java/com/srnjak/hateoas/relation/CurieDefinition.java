package com.srnjak.hateoas.relation;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

/**
 * A definition class for a curie.
 */
@Value
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@FieldNameConstants
public class CurieDefinition {

    /**
     * The curie prefix
     */
    @EqualsAndHashCode.Include
    private String prefix;

    /**
     * The value where curie is pointing to
     */
    private String href;
}
