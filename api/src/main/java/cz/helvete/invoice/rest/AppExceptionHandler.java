package cz.helvete.invoice.rest;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class AppExceptionHandler implements ExceptionMapper<AppException> {

    @Inject
    public Logger logger;

    @Override
    public Response toResponse(AppException e) {
        logger.log(Level.INFO, e.getCode() + ": " + e.getMessage());
        return new BaseResponse(e.getCode(), e.getMessage(), e.getData()).build();
    }
}
