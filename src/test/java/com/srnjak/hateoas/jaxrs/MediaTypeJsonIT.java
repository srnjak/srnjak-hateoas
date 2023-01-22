package com.srnjak.hateoas.jaxrs;

import com.srnjak.hateoas.examples.resources.TestResource;
import com.srnjak.hateoas.test.utils.FileUtils;
import com.srnjak.hateoas.test.utils.JsonUtils;
import com.srnjak.hateoas.test.utils.jaxrs.DebugMapper;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MediaTypeJsonIT extends JerseyTest {

    public static final String SNIPPET_LOCATION = "output/json";

    @Override
    protected Application configure() {

        ResourceConfig config = new ResourceConfig(
                TestResource.class,
                HateoasFilter.class);
        config.register(DebugMapper.class);

        return config;
    }

    @Test
    public void shouldReturnJson_When_Single() {

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

        FileUtils.write(
                SNIPPET_LOCATION, "single.json", JsonUtils.prettify(output));
    }

    @Test
    public void shouldReturnJson_When_GenericSingle() {

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

        FileUtils.write(
                SNIPPET_LOCATION,
                "single-generic.json",
                JsonUtils.prettify(output));
    }

    @Test
    public void shouldReturnJson_When_Collection() {

        Response response = target("test/collection")
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

        FileUtils.write(
                SNIPPET_LOCATION,
                "collection.json",
                JsonUtils.prettify(output));
    }
}