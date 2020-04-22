package com.srnjak.hateoas.jaxrs;

import com.srnjak.hateoas.examples.resources.TestResource;
import com.srnjak.hateoas.mediatype.hal.jaxrs.HalCollectionWriter;
import com.srnjak.hateoas.mediatype.hal.jaxrs.HalEntityWriter;
import com.srnjak.hateoas.mediatype.hal.jaxrs.HalHypermediaWriter;
import com.srnjak.hateoas.mediatype.hal.jaxrs.HalMediaType;
import com.srnjak.hateoas.test.utils.FileUtils;
import com.srnjak.hateoas.test.utils.XmlUtils;
import com.srnjak.hateoas.test.utils.jaxrs.DebugMapper;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MediaTypeHalXmlIT extends JerseyTest {

    public static final String SNIPPET_LOCATION = "output/hal-xml";

    @Override
    protected Application configure() {

        ResourceConfig config = new ResourceConfig(
                TestResource.class,
                HateoasFilter.class,
                HalCollectionWriter.class,
                HalHypermediaWriter.class,
                HalEntityWriter.class);
        config.register(DebugMapper.class);

        return config;
    }

    @Test
    public void shouldReturnHalXml_When_Single() {

        Response response = target("test/single")
                .request(HalMediaType.APPLICATION_HAL_XML)
                .get();

        assertEquals(200, response.getStatus());
        assertEquals(
                HalMediaType.APPLICATION_HAL_XML_TYPE,
                response.getMediaType());

        String output = response.readEntity(String.class);
        System.out.println(output);

        assertTrue(output.contains("<resource"));

        FileUtils.write(
                SNIPPET_LOCATION,
                "single.xml",
                XmlUtils.prettify(output));
    }

    @Test
    public void shouldReturnHalXml_When_GenericSingle() {

        Response response = target("test/generic")
                .request(HalMediaType.APPLICATION_HAL_XML)
                .get();

        assertEquals(200, response.getStatus());
        assertEquals(
                HalMediaType.APPLICATION_HAL_XML_TYPE,
                response.getMediaType());

        String output = response.readEntity(String.class);
        System.out.println(output);

        assertTrue(output.contains("<resource"));

        FileUtils.write(
                SNIPPET_LOCATION,
                "single-generic.xml",
                XmlUtils.prettify(output));
    }

    @Test
    public void shouldReturnHalXml_When_Collection() {

        Response response = target("test/collection")
                .request(HalMediaType.APPLICATION_HAL_XML)
                .get();

        assertEquals(200, response.getStatus());
        assertEquals(
                HalMediaType.APPLICATION_HAL_XML_TYPE,
                response.getMediaType());

        String output = response.readEntity(String.class);
        System.out.println(output);

        assertTrue(output.contains("<resource"));
        assertTrue(output.contains("rel=\"item\""));

        FileUtils.write(
                SNIPPET_LOCATION,
                "collection.xml",
                XmlUtils.prettify(output));
    }
}