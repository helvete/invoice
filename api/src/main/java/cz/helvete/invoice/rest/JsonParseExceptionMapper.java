package cz.helvete.invoice.rest;

import com.fasterxml.jackson.core.JsonParseException;
import javax.annotation.Priority;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Priority(1)
@Provider
public class JsonParseExceptionMapper implements ExceptionMapper<JsonParseException> {
    @Override
    public Response toResponse(JsonParseException exception) {
        return new BaseResponse(ResponseResultCode.BAD_REQUEST).build();
    }
}
