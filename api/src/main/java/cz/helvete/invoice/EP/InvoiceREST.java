package cz.helvete.invoice.EP;

import cz.helvete.invoice.auth.AllowUnauthenticated;
import cz.helvete.invoice.db.InvoiceDAO;
import cz.helvete.invoice.db.SubjectDAO;
import cz.helvete.invoice.db.entity.Invoice;
import cz.helvete.invoice.db.entity.Item;
import cz.helvete.invoice.db.entity.Subject;
import cz.helvete.invoice.rest.AppException;
import cz.helvete.invoice.rest.ResponseResultCode;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("invoice")
public class InvoiceREST {
    /*
     * TODO: move non-trivial stuff to a business logic service
     */

    @Inject
    private InvoiceDAO invoiceDAO;

    @Inject
    private SubjectDAO subjectDAO;

    @GET
    @AllowUnauthenticated
    public List<Invoice> getAll() {
        return invoiceDAO.getAll();
    }

    @GET
    @AllowUnauthenticated
    @Path("/{invoiceId}")
    public Invoice get(@PathParam("invoiceId") Integer invoiceId) throws AppException {
        Invoice invoice = invoiceDAO.findById(invoiceId);
        if (invoice == null) {
            throw new AppException(ResponseResultCode.NOT_FOUND);
        }
        return invoice;
    }

    @POST
    @AllowUnauthenticated
    public Invoice insert(Invoice invoice) throws AppException {
        Subject acceptor = subjectDAO.findById(invoice.getAcceptorId());
        Subject provider = subjectDAO.findById(invoice.getProviderId());
        if (acceptor == null || provider == null) {
            throw new AppException(
                    ResponseResultCode.BAD_REQUEST,
                    "acceptor_id and/or provider_id invalid or missing");
        }
        invoice.setAcceptor(acceptor);
        invoice.setProvider(provider);
        return invoiceDAO.persist(invoice);
    }

    @POST
    @AllowUnauthenticated
    @Path("/{invoiceId}/item")
    public Invoice addItem(
            @PathParam("invoiceId") Integer invoiceId,
            Item item
    ) throws AppException {
        Invoice invoice = invoiceDAO.findById(invoiceId);
        if (invoice == null) {
            throw new AppException(ResponseResultCode.NOT_FOUND);
        }
        item.setInvoice(invoice);
        invoice.getItems().add(item);
        return invoiceDAO.merge(invoice);
    }

    @DELETE
    @AllowUnauthenticated
    @Path("/{invoiceId}/item/{itemId}")
    public Invoice deleteItem(
            @PathParam("invoiceId") Integer invoiceId,
            @PathParam("itemId") Integer itemId
    ) throws AppException {
        Invoice invoice = invoiceDAO.findById(invoiceId);
        if (invoice == null) {
            throw new AppException(ResponseResultCode.NOT_FOUND);
        }
        // TODO: improve algorithm
        int index = 0;
        for (Item item : invoice.getItems()) {
            if (item.getId().equals(itemId)) {
                invoice.getItems().remove(index);
                index = -1;
                break;
            }
            index++;
        }
        if (index < 0) {
            return invoiceDAO.merge(invoice);
        }
        throw new AppException(ResponseResultCode.NOT_FOUND);
    }
}
