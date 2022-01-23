package cz.helvete.invoice.rest;

public enum ResponseResultCode {
    OK(200),
    BAD_REQUEST(400),
    INVALID_CREDENTIALS(401),
    JWT_INVALID(401),
    HASH_EXPIRED(403),
    ACCOUNT_INACTIVE(403),
    NOT_FOUND(404),
    METHOD_NOT_ALLOWED(405),
    MISDIRECTED_REQUEST(421),
    UNPROCESSABLE_ENTITY(422),
    SERVER_ERROR(500);

    private int statusCode;

    private ResponseResultCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int statusCode() {
        return statusCode;
    }
}
