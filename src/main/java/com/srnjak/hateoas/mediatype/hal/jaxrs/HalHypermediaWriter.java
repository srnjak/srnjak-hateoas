package com.srnjak.hateoas.mediatype.hal.jaxrs;

import com.srnjak.hateoas.EntityModel;
import com.srnjak.hateoas.HypermediaModel;
import com.srnjak.hateoas.mediatype.hal.HalMapper;
import com.srnjak.hateoas.mediatype.hal.json.HalObjectJson;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * JAX-RS message body writer for {@link HypermediaModel} into HAL json
 * representation.
 * <br>
 * <br> If client requests application/hal+json type, this writer comes in and
 * prepares the response.
 */
@Provider
@Produces(HalMediaType.APPLICATION_HAL_JSON)
public class HalHypermediaWriter implements MessageBodyWriter<HypermediaModel> {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isWriteable(
            Class<?> aClass,
            Type type,
            Annotation[] annotations,
            MediaType mediaType) {

        return EntityModel.class.isAssignableFrom(aClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeTo(
            HypermediaModel hypermediaModel,
            Class<?> aClass,
            Type type,
            Annotation[] annotations,
            MediaType mediaType,
            MultivaluedMap<String, Object> multivaluedMap,
            OutputStream outputStream)
            throws IOException, WebApplicationException {

        System.out.println(hypermediaModel);

        String json = Optional.of(hypermediaModel)
                .map(HalMapper::toHalObject)
                .map(x -> {
                    System.out.println(x);
                    return x;
                })
                .map(HalObjectJson::new)
                .map(HalObjectJson::toJsonObject)
                .map(Object::toString)
                .get();

        System.out.println(json);

        outputStream.write(json.getBytes(StandardCharsets.UTF_8));
    }
}
