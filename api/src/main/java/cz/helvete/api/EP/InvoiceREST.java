package cz.helvete.api.EP;

import cz.helvete.api.rest.AppException;
import cz.helvete.api.rest.BaseResponse;
import cz.helvete.api.rest.ResponseResultCode;
import cz.helvete.api.auth.AllowUnauthenticated;
import cz.helvete.api.db.entity.Invoice;
import cz.helvete.api.db.InvoiceDAO;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
        return new BaseResponse(ResponseResultCode.OK)
            .data(invoiceDAO.getAll())
            .build();
    }

    @GET
    @AllowUnauthenticated
    @Path("/{invoiceId}")
    public Response get(@PathParam("invoiceId") Integer invoiceId) throws AppException {
        Invoice invoice = invoiceDAO.findById(invoiceId);
        if (invoice == null) {
            throw new AppException(ResponseResultCode.NOT_FOUND);
        }
        return new BaseResponse(ResponseResultCode.OK).data(invoice).build();
    }
}
