package com.srnjak.hateoas.mediatype.hal.jaxrs;

import com.srnjak.hateoas.jaxrs.HateoasFilter;
import com.srnjak.testing.json.AssertJson;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static org.junit.jupiter.api.Assertions.*;

public class HalHypermediaWriterIT extends JerseyTest {

    @Provider
    public static class DebugMapper implements ExceptionMapper<Throwable> {
        @Override
        public Response toResponse(Throwable e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

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
    public void getSingle_When_HalJson() {

        Response response = target("test/single")
                .request(HalMediaType.APPLICATION_HAL_JSON)
                .get();

        assertEquals(200, response.getStatus());
        assertEquals(
                HalMediaType.APPLICATION_HAL_JSON_TYPE,
                response.getMediaType());

        String output = response.readEntity(String.class);
        System.out.println(output);

        assertTrue(output.contains("_links"));
    }

    @Test
    public void getSingle_When_HalXml() {

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
    }

    @Test
    public void getSingle_When_Json() {

        Response response = target("test/single")
                .request(MediaType.APPLICATION_JSON)
                .get();

        assertEquals(200, response.getStatus());
        assertEquals(
                MediaType.APPLICATION_JSON_TYPE,
                response.getMediaType());

        String output = response.readEntity(String.class);
        System.out.println(output);

        assertFalse(output.contains("_links"));
    }

    @Test
    public void getSingle_When_Xml() {

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
    }

    @Test
    public void getSingle_When_GenericEntity_HalJson() {

        Response response = target("test/generic")
                .request(HalMediaType.APPLICATION_HAL_JSON)
                .get();

        assertEquals(200, response.getStatus());
        assertEquals(
                HalMediaType.APPLICATION_HAL_JSON_TYPE,
                response.getMediaType());

        String output = response.readEntity(String.class);
        System.out.println(output);

        assertTrue(output.contains("_links"));
    }

    @Test
    public void getSingle_When_GenericEntity_HalXml() {

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
    }

    @Test
    public void getSingle_When_GenericEntity_Json() {

        Response response = target("test/generic")
                .request(MediaType.APPLICATION_JSON)
                .get();

        assertEquals(200, response.getStatus());
        assertEquals(
                MediaType.APPLICATION_JSON_TYPE,
                response.getMediaType());

        String output = response.readEntity(String.class);
        System.out.println(output);

        assertFalse(output.contains("_links"));
    }

    @Test
    public void getSingle_When_GenericEntity_Xml() {

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
    }

    @Test
    public void getSingle_When_Collection_HalJson() {

        Response response = target("test")
                .request(HalMediaType.APPLICATION_HAL_JSON)
                .get();

        assertEquals(200, response.getStatus());
        assertEquals(
                HalMediaType.APPLICATION_HAL_JSON_TYPE,
                response.getMediaType());

        String output = response.readEntity(String.class);
        System.out.println(output);

        assertTrue(output.contains("_links"));
        assertTrue(output.contains("_embedded"));
        assertTrue(output.contains("\"item\":"));
    }

    @Test
    public void getSingle_When_Collection_HalXml() {

        Response response = target("test")
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
    }

    @Test
    public void getSingle_When_Collection_Json() {

        Response response = target("test")
                .request(MediaType.APPLICATION_JSON)
                .get();

        assertEquals(200, response.getStatus());
        assertEquals(
                MediaType.APPLICATION_JSON_TYPE,
                response.getMediaType());

        String output = response.readEntity(String.class);
        System.out.println(output);

        assertFalse(output.contains("_links"));
        assertFalse(output.contains("_embedded"));
        assertFalse(output.contains("\"item\":"));
    }

    @Test
    public void getSingle_When_Collection_Xml() {

        Response response = target("test")
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
    }
}