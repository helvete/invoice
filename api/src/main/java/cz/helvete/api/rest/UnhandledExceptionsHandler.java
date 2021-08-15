package cz.helvete.api.rest;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class UnhandledExceptionsHandler implements ExceptionMapper<Exception> {

    @Inject
    private Logger log;

    @Inject
    private HttpServletRequest hsr;

    @Override
    public Response toResponse(Exception exception){
        log.log(Level.WARNING, String.format(
                    "Exception thrown from REST API on %s : %s",
                    hsr.getMethod(),
                    hsr.getRequestURI()), exception);
        ResponseResultCode rrc;
        String message = exception.getMessage();
        switch (exception.getClass().getName()) {
        case "javax.ws.rs.NotAllowedException":
            rrc = ResponseResultCode.METHOD_NOT_ALLOWED;
            break;
        case "org.glassfish.jersey.server.ParamException$PathParamException":
        case "org.glassfish.jersey.server.ParamException$QueryParamException":
        case "javax.ws.rs.NotFoundException":
            rrc = ResponseResultCode.NOT_FOUND;
            message = "Resource not found";
            break;
        default:
            rrc = ResponseResultCode.SERVER_ERROR;
            message = null; // do not leak server error specifics (sql, ...)
            break;
        }

        return new BaseResponse(rrc, message).build();
    }
}
