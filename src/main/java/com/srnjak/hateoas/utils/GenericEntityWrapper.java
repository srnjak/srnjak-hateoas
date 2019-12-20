package com.srnjak.hateoas.utils;

import lombok.Value;

import java.lang.reflect.Type;

/**
 * Wrapper for entity of a generic type.
 */
@Value
public class GenericEntityWrapper {

    /**
     * The entity object
     */
    Object entity;

    /**
     * The generic type of an entity object
     */
    Type genericType;
}
