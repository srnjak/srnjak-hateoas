package com.srnjak.hateoas.mediatype.hal.jaxrs;

import lombok.experimental.UtilityClass;

import jakarta.ws.rs.core.MediaType;

/**
 * HAL media types
 */
@UtilityClass
public class HalMediaType {

    /**
     * HAL+JSON media type string
     */
    public static final String APPLICATION_HAL_JSON = "application/hal+json";

    /**
     * HAL+JSON media type string
     */
    public static final String APPLICATION_HAL_XML = "application/hal+xml";

    /**
     * HAL+JSON media type
     */
    public static final MediaType APPLICATION_HAL_JSON_TYPE =
            new MediaType("application", "hal+json");

    /**
     * HAL+JSON media type
     */
    public static final MediaType APPLICATION_HAL_XML_TYPE =
            new MediaType("application", "hal+xml");
}
