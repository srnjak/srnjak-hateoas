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

@Provider
@Produces(HalMediaType.APPLICATION_HAL_JSON)
public class HalObjectWriter implements MessageBodyWriter<EntityModel<?>> {

    @Override
    public boolean isWriteable(
            Class<?> aClass,
            Type type,
            Annotation[] annotations,
            MediaType mediaType) {

        return EntityModel.class.isAssignableFrom(aClass);
    }

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

        outputStream.write(json.getBytes(Charset.forName("UTF-8")));
    }
}
