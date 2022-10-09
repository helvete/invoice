package cz.helvete.invoice.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Priority;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@PreMatching
@Priority(CORSFilter.BEFORE_AUTHENTICATION)
@Provider
public class CORSFilter implements ContainerResponseFilter, ContainerRequestFilter {

    static final int BEFORE_AUTHENTICATION = 999;

    private static Map<String, String> corsHeaders = new HashMap<>();

    static {
        corsHeaders.put("Access-Control-Allow-Origin", "*");
        corsHeaders.put(
                "Access-Control-Allow-Headers",
                "origin, content-type, accept, x-jwt-assertion");
        corsHeaders.put("Access-Control-Allow-Credentials", "true");
        corsHeaders.put(
                "Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        corsHeaders.put("Access-Control-Max-Age", "86400");
        corsHeaders.put("Access-Control-Expose-Headers", "Date");
    }

    @Override
    public void filter(
            ContainerRequestContext requestContext,
            ContainerResponseContext responseContext
    ) throws IOException {
        corsHeaders.forEach((k,v) -> responseContext.getHeaders().add(k, v));
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (requestContext.getMethod().equals(HttpMethod.OPTIONS)) {
            requestContext.abortWith(Response.ok().build());
        }
    }
}
