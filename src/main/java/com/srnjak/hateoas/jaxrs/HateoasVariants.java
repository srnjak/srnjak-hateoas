package com.srnjak.hateoas.jaxrs;

import com.srnjak.hateoas.mediatype.hal.jaxrs.HalMediaType;
import lombok.experimental.UtilityClass;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Variant;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Constants of the HATEOAS variants
 */
@UtilityClass
public class HateoasVariants {

    /**
     * The variant of application/hal+json type.
     */
    public static final Variant VARIANT_APPLICATION_HAL_JSON =
            new Variant(
                    HalMediaType.APPLICATION_HAL_JSON_TYPE,
                    (String) null, null);

    /**
     * The variant of application/hal+xml type.
     */
    public static final Variant VARIANT_APPLICATION_HAL_XML =
            new Variant(
                    HalMediaType.APPLICATION_HAL_XML_TYPE,
                    (String) null, null);

    /**
     * The list of supported variants.
     */
    public static final List<Variant> SUPPORTED_VARIANTS =
            Stream.of(
                    HateoasVariants.VARIANT_APPLICATION_HAL_JSON,
                    HateoasVariants.VARIANT_APPLICATION_HAL_XML)
                    .collect(Collectors.collectingAndThen(
                            Collectors.toList(),
                            Collections::unmodifiableList));
}
