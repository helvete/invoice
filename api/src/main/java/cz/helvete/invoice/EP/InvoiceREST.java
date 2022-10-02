package cz.helvete.invoice.EP;

import cz.helvete.invoice.db.InvoiceDAO;
import cz.helvete.invoice.db.SubjectDAO;
import cz.helvete.invoice.db.entity.Invoice;
import cz.helvete.invoice.db.entity.Item;
import cz.helvete.invoice.db.entity.Subject;
import cz.helvete.invoice.entity.InvoiceBrief;
import cz.helvete.invoice.rest.AppException;
import cz.helvete.invoice.rest.ResponseResultCode;
import cz.helvete.invoice.template.TemplateRenderer;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

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

    @Inject
    private TemplateRenderer templater;

    @GET
    public List<InvoiceBrief> getAll() {
        return invoiceDAO
            .getAll()
            .stream()
            .map(i -> new InvoiceBrief(i))
            .collect(Collectors.toList());
    }

    @GET
    @Path("/{invoiceId}")
    public Invoice get(@PathParam("invoiceId") Integer invoiceId) {
        Invoice invoice = invoiceDAO.findById(invoiceId);
        if (invoice == null) {
            throw new AppException(ResponseResultCode.NOT_FOUND);
        }
        return invoice;
    }

    @POST
    public Invoice insert(Invoice invoice) {
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
    @Path("/{invoiceId}/item")
    public Invoice addItem(@PathParam("invoiceId") Integer invoiceId, Item item) {
        Invoice invoice = invoiceDAO.findById(invoiceId);
        if (invoice == null) {
            throw new AppException(ResponseResultCode.NOT_FOUND);
        }
        item.setInvoice(invoice);
        invoice.getItems().add(item);
        return invoiceDAO.merge(invoice);
    }

    @DELETE
    @Path("/{invoiceId}/item/{itemId}")
    public Invoice deleteItem(
            @PathParam("invoiceId") Integer invoiceId,
            @PathParam("itemId") Integer itemId
    ) {
        Invoice invoice = invoiceDAO.findById(invoiceId);
        if (invoice == null) {
            throw new AppException(ResponseResultCode.NOT_FOUND);
        }
        // TODO: improve this algorithm
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

    @GET
    @Path("/{invoiceId}/render")
    @Produces({MediaType.TEXT_HTML + "; charset=UTF-8", MediaType.APPLICATION_JSON})
    public String render(@PathParam("invoiceId") Integer invoiceId) {
        Invoice invoice = invoiceDAO.findById(invoiceId);
        if (invoice == null) {
            throw new AppException(ResponseResultCode.NOT_FOUND);
        }
        return templater.render(invoice);
    }

    @GET
    @Path("/{invoiceId}/pdf")
    @Produces({MediaType.APPLICATION_OCTET_STREAM, MediaType.APPLICATION_JSON})
    public Response pdf(@PathParam("invoiceId") Integer invoiceId) {
        Invoice invoice = invoiceDAO.findById(invoiceId);
        if (invoice == null) {
            throw new AppException(ResponseResultCode.NOT_FOUND);
        }
        StreamingOutput streamingOutput = output -> {
            byte[] data = templater.renderPdf(invoice).toByteArray();
            output.write(data);
            output.flush();
        };
        String contentDisposition = String.format(
                "attachment; filename=\"invoice_%s.pdf\"", invoice.getNumber());
        return Response
                .ok(streamingOutput)
                .header("Content-Disposition", contentDisposition)
                .header("Content-Type", "application/pdf")
                .header("Content-Transfer-Encoding", "binary")
                .header("Connection", "Keep-Alive")
                .build();
    }
}
