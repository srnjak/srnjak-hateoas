package com.srnjak.hateoas.test.utils.jaxrs;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DebugMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable e) {
        e.printStackTrace();
        return Response.serverError().build();
    }
}