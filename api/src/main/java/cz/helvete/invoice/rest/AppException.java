package cz.helvete.invoice.rest;

public class AppException extends RuntimeException {

    private ResponseResultCode code;
    private Object data;

    public AppException(ResponseResultCode code) {
        this(code, null, null);
    }

    public AppException(ResponseResultCode code, Object data) {
        this(code, null, data);
    }

    public AppException(ResponseResultCode code, String message) {
        this(code, message, null);
    }

    public AppException(ResponseResultCode code, String message, Object data) {
        super(message);
        this.code = code;
        this.data = data;
    }

    public ResponseResultCode getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }
}
