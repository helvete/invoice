package cz.helvete.invoice.EP;

import cz.helvete.invoice.auth.AllowUnauthenticated;
import cz.helvete.invoice.rest.RootInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("")
public class RootREST {

    @GET
    @AllowUnauthenticated
    public RootInfo get() {
        return new RootInfo("John Doe");
    }
}
