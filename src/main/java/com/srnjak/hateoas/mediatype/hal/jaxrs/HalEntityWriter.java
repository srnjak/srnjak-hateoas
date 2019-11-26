package com.srnjak.hateoas.mediatype.hal.jaxrs;

import com.srnjak.hateoas.EntityModel;
import com.srnjak.hateoas.mediatype.hal.HalMapper;

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
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * JAX-RS message body writer for {@link EntityModel} into HAL json
 * representation.
 * <br>
 * <br> If client requests application/hal+json type, this writer comes in and
 * prepares the response.
 */
@Provider
@Produces(HalMediaType.APPLICATION_HAL_JSON)
public class HalEntityWriter implements MessageBodyWriter<EntityModel<?>> {

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
            EntityModel<?> entityModel,
            Class<?> aClass,
            Type type,
            Annotation[] annotations,
            MediaType mediaType,
            MultivaluedMap<String, Object> multivaluedMap,
            OutputStream outputStream)
            throws IOException, WebApplicationException {

        String json = HalMapper.toHalObject(entityModel)
                .toJsonObject()
                .toString();

        outputStream.write(json.getBytes(StandardCharsets.UTF_8));
    }
}
