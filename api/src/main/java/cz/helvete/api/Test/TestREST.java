package cz.helvete.api.Test;

import cz.helvete.api.auth.AllowUnauthenticated;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("test")
public class TestREST {

    @Inject
    private EntityManager em;

    @GET
    @AllowUnauthenticated
    public Response test() {
        return Response.ok().entity("{}").build();
    }
}
