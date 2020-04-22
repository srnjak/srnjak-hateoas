package com.srnjak.hateoas.test.utils.jaxrs;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DebugMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable e) {
        e.printStackTrace();
        return Response.serverError().build();
    }
}