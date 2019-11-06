package com.srnjak.hateoas.hal;

import lombok.experimental.UtilityClass;

import javax.ws.rs.core.MediaType;

@UtilityClass
public class HalMediaType {

    public static final String APPLICATION_HAL_JSON = "application/hal+json";
    public static final MediaType APPLICATION_HAL_JSON_TYPE =
            new MediaType("application", "hal+json");
}
