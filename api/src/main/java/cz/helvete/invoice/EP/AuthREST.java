package cz.helvete.invoice.EP;

import cz.helvete.invoice.auth.AllowUnauthenticated;
import cz.helvete.invoice.auth.AuthService;
import cz.helvete.invoice.auth.SecurityService;
import cz.helvete.invoice.db.entity.Account;
import cz.helvete.invoice.entity.AuthRequest;
import cz.helvete.invoice.entity.AuthResponse;
import cz.helvete.invoice.rest.AppException;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/auth")
public class AuthREST {

    @Inject
    private SecurityService securityService;

    @Inject
    private AuthService authService;

    // TODO: Dev endpoint only!
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @AllowUnauthenticated
    @Path("/{password}")
    public String generate(@PathParam("password") String password) {
        return securityService.hashPassword(password);
    }

    // TODO: Change to POST
    @GET
    @AllowUnauthenticated
    @Path("/login/{email}/{password}")
    public AuthResponse generate(
            @PathParam("email") String email,
            @PathParam("password") String password
    ) throws AppException {
        return authService.login(new AuthRequest(email, password));
    }

}
