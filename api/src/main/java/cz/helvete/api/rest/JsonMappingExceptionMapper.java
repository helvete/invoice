package cz.helvete.api.rest;

import com.fasterxml.jackson.databind.JsonMappingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Priority(1)
@Provider
public class JsonMappingExceptionMapper implements ExceptionMapper<JsonMappingException> {

    @Inject
    Logger logger;

    @Override
    public Response toResponse(JsonMappingException exception) {
        logger.log(Level.INFO, exception.getMessage());
        return new BaseResponse(ResponseResultCode.BAD_REQUEST).build();
    }
}
