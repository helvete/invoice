package cz.helvete.invoice.rest;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@JsonInclude(Include.NON_NULL)
public class BaseResponse {

    private int status;
    private String code;
    private String message;
    private Object data;

    public BaseResponse(ResponseResultCode rrc, String message, Object data) {
        this.status = rrc.statusCode();
        this.code = rrc.name();
        this.message = message;
        this.data = data;
    }

    public BaseResponse(ResponseResultCode rrc) {
        this.status = rrc.statusCode();
        this.code = rrc.name();
    }

    public BaseResponse(ResponseResultCode rrc, String message) {
        this.status = rrc.statusCode();
        this.code = rrc.name();
        this.message = message;
    }

    public BaseResponse data(Object data) {
        this.data = data;
        return this;
    }

    public int getStatus() {
        return status;
    }
    public String getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
    public Object getData() {
        return data;
    }

    /**
     * Build full JAX-RS Response from this object
     *
     * @return Response
     */
    public Response build() {
        return Response.status(status)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .entity(this)
                .build();
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "{\"status\":500,\"code\":\"SERVER_ERROR\"}";
        }
    }
}
