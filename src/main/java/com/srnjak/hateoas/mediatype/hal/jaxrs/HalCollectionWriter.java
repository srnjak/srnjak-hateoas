package com.srnjak.hateoas.mediatype.hal.jaxrs;

import com.srnjak.hateoas.CollectionModel;
import com.srnjak.hateoas.EntityModel;
import com.srnjak.hateoas.mediatype.hal.HalMapper;
import com.srnjak.hateoas.mediatype.hal.json.HalObjectJson;
import com.srnjak.hateoas.mediatype.hal.xml.HalObjectXml;
import org.w3c.dom.Document;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.ext.Provider;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * JAX-RS message body writer for {@link CollectionModel} into HAL json
 * representation.
 * <br>
 * <br> If client requests application/hal+json type, this writer comes in and
 * prepares the response.
 */
@Provider
@Produces({HalMediaType.APPLICATION_HAL_JSON, HalMediaType.APPLICATION_HAL_XML})
public class HalCollectionWriter
        implements MessageBodyWriter<CollectionModel<?>> {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isWriteable(
            Class<?> aClass,
            Type type,
            Annotation[] annotations,
            MediaType mediaType) {

        return CollectionModel.class.isAssignableFrom(aClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeTo(
            CollectionModel<?> collectionModel,
            Class<?> aClass,
            Type type,
            Annotation[] annotations,
            MediaType mediaType,
            MultivaluedMap<String, Object> multivaluedMap,
            OutputStream outputStream)
            throws IOException, WebApplicationException {

        Map<MediaType, Function<CollectionModel<?>, String>> functionMap =
                new HashMap<>();
        functionMap.put(HalMediaType.APPLICATION_HAL_JSON_TYPE, this::getJson);
        functionMap.put(HalMediaType.APPLICATION_HAL_XML_TYPE, this::getXml);

        byte[] bytes = Optional.of(collectionModel)
                .map(functionMap.get(mediaType))
                .map(s -> s.getBytes(StandardCharsets.UTF_8))
                .get();

        outputStream.write(bytes);
    }

    private String getJson(CollectionModel<?> collectionModel) {
        return Optional.of(collectionModel)
                .map(HalMapper::toHalObject)
                .map(HalObjectJson::new)
                .map(HalObjectJson::toJsonObject)
                .map(Object::toString)
                .get();
    }

    private String getXml(CollectionModel<?> collectionModel) {
        return Optional.of(collectionModel)
                .map(HalMapper::toHalObject)
                .map(HalObjectXml::new)
                .map(HalObjectXml::toXmlDocument)
                .map(HalCollectionWriter::documentToString)
                .get();
    }

    private static String documentToString(Document document) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            StringWriter sw = new StringWriter();
            t.transform(new DOMSource(document), new StreamResult(sw));

            return sw.toString();
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }
}
