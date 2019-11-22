package com.srnjak.hateoas.jaxrs;

import com.srnjak.hateoas.mediatype.hal.jaxrs.HalMediaType;
import lombok.experimental.UtilityClass;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Variant;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class HateoasVariants {

    public static final Variant VARIANT_APPLICATION_JSON =
            new Variant(MediaType.APPLICATION_JSON_TYPE, (String) null, null);

    public static final Variant VARIANT_APPLICATION_HAL_JSON =
            new Variant(
                    HalMediaType.APPLICATION_HAL_JSON_TYPE,
                    (String) null, null);

    public static final List<Variant> SUPPORTED_VARIANTS =
            Stream.of(
                    HateoasVariants.VARIANT_APPLICATION_JSON,
                    HateoasVariants.VARIANT_APPLICATION_HAL_JSON)
                    .collect(Collectors.collectingAndThen(
                            Collectors.toList(),
                            Collections::unmodifiableList));
}