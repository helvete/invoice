package cz.helvete.api.EP;

import cz.helvete.api.auth.AllowUnauthenticated;
import cz.helvete.api.db.InvoiceDAO;
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
@Path("invoice")
public class InvoiceREST {

    @Inject
    private InvoiceDAO invoiceDAO;

    @GET
    @AllowUnauthenticated
    public Response getAll() {
        return Response.ok().entity(invoiceDAO.getAll()).build();
    }

    @GET
    @AllowUnauthenticated
    @Path("/{invoiceId}")
    public Response get(@PathParam("invoiceId") Integer invoiceId) {
        return Response.ok().entity(invoiceDAO.findById(invoiceId)).build();
    }
}
