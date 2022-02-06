package cz.helvete.invoice.EP;

import cz.helvete.invoice.db.SubjectDAO;
import cz.helvete.invoice.db.entity.Subject;
import cz.helvete.invoice.entity.SubjectBrief;
import cz.helvete.invoice.rest.AppException;
import cz.helvete.invoice.rest.ResponseResultCode;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("subject")
public class SubjectREST {

    @Inject
    private SubjectDAO subjectDAO;

    @GET
    public List<SubjectBrief> getAll() {
        return subjectDAO
            .getAll()
            .stream()
            .map(i -> new SubjectBrief(i))
            .collect(Collectors.toList());
    }

    @GET
    @Path("/{subjectId}")
    public Subject get(@PathParam("subjectId") Integer subjectId) {
        Subject subject = subjectDAO.findById(subjectId);
        if (subject == null) {
            throw new AppException(ResponseResultCode.NOT_FOUND);
        }
        return subject;
    }

    @POST
    public Subject insert(Subject invoice) {
        // TODO Address
        //Subject acceptor = subjectDAO.findById(invoice.getAcceptorId());
        //Subject provider = subjectDAO.findById(invoice.getProviderId());
        //if (acceptor == null || provider == null) {
        //    throw new AppException(
        //            ResponseResultCode.BAD_REQUEST,
        //            "acceptor_id and/or provider_id invalid or missing");
        //}
        //invoice.setAcceptor(acceptor);
        return subjectDAO.persist(invoice);
    }
}
