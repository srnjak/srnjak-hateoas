package com.srnjak.hateoas.mediatype.hal.jaxrs;

import com.srnjak.hateoas.jaxrs.HateoasFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

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
    public void get() {

        String response1 = target("test/single").request(HalMediaType.APPLICATION_HAL_JSON).get(String.class);
        String response2 = target("test/single").request(HalMediaType.APPLICATION_HAL_XML).get(String.class);
        String response3 = target("test/single").request(MediaType.APPLICATION_JSON).get(String.class);
        String response4 = target("test/single").request(MediaType.APPLICATION_XML).get(String.class);

        System.out.println(response1);
        System.out.println(response2);
        System.out.println(response3);
        System.out.println(response4);
    }

    @Test
    public void generic() {

        String response1 = target("test/generic").request(HalMediaType.APPLICATION_HAL_JSON).get(String.class);
        String response2 = target("test/generic").request(HalMediaType.APPLICATION_HAL_XML).get(String.class);
        String response3 = target("test/generic").request(MediaType.APPLICATION_JSON).get(String.class);
        String response4 = target("test/generic").request(MediaType.APPLICATION_XML).get(String.class);

        System.out.println(response1);
        System.out.println(response2);
        System.out.println(response3);
        System.out.println(response4);
    }

    @Test
    public void getAll() {

        String response1 = target("test").request(HalMediaType.APPLICATION_HAL_JSON).get(String.class);
        String response2 = target("test").request(HalMediaType.APPLICATION_HAL_XML).get(String.class);
        String response3 = target("test").request(MediaType.APPLICATION_JSON).get(String.class);
        String response4 = target("test").request(MediaType.APPLICATION_XML).get(String.class);

        System.out.println(response1);
        System.out.println(response2);
        System.out.println(response3);
        System.out.println(response4);
    }

}