package cz.helvete.invoice.EP;

import cz.helvete.invoice.auth.entity.AuthUser;
import cz.helvete.invoice.entity.RootInfo;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("")
public class RootREST {

    @Inject
    private AuthUser user;

    @GET
    public RootInfo get() {
        return new RootInfo(user.getEmail());
    }
}
