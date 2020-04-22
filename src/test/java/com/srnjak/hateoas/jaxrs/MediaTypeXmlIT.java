package com.srnjak.hateoas.jaxrs;

import com.srnjak.hateoas.examples.resources.TestResource;
import com.srnjak.hateoas.test.utils.FileUtils;
import com.srnjak.hateoas.test.utils.XmlUtils;
import com.srnjak.hateoas.test.utils.jaxrs.DebugMapper;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MediaTypeXmlIT extends JerseyTest {

    public static final String SNIPPET_LOCATION = "output/xml";

    @Override
    protected Application configure() {

        ResourceConfig config = new ResourceConfig(
                TestResource.class,
                HateoasFilter.class);
        config.register(DebugMapper.class);

        return config;
    }

    @Test
    public void shouldReturnXml_When_Single() {

        Response response = target("test/single")
                .request(MediaType.APPLICATION_XML)
                .get();

        assertEquals(200, response.getStatus());
        assertEquals(
                MediaType.APPLICATION_XML_TYPE,
                response.getMediaType());

        String output = response.readEntity(String.class);
        System.out.println(output);

        assertFalse(output.contains("<resource"));

        FileUtils.write(
                SNIPPET_LOCATION, "single.xml", XmlUtils.prettify(output));
    }

    @Test
    public void shouldReturnXml_When_GenericSingle() {

        Response response = target("test/generic")
                .request(MediaType.APPLICATION_XML)
                .get();

        assertEquals(200, response.getStatus());
        assertEquals(
                MediaType.APPLICATION_XML_TYPE,
                response.getMediaType());

        String output = response.readEntity(String.class);
        System.out.println(output);

        assertFalse(output.contains("<resource"));

        FileUtils.write(
                SNIPPET_LOCATION,
                "single-generic.xml",
                XmlUtils.prettify(output));
    }

    @Test
    public void shouldReturnXml_When_Collection() {

        Response response = target("test/collection")
                .request(MediaType.APPLICATION_XML)
                .get();

        assertEquals(200, response.getStatus());
        assertEquals(
                MediaType.APPLICATION_XML_TYPE,
                response.getMediaType());

        String output = response.readEntity(String.class);
        System.out.println(output);

        assertFalse(output.contains("<resource"));
        assertFalse(output.contains("rel=\"item\""));

        FileUtils.write(
                SNIPPET_LOCATION,
                "collection.xml",
                XmlUtils.prettify(output));
    }
}