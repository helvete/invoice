package cz.helvete.invoice.auth.jwt.entity;

public enum ParserResultEnum {
    OK,
    TOKEN_EXPIRED,
    TOKEN_MALFORMED,
    SIGNATURE_INVALID,
    NOT_A_TOKEN,
    TOKEN_DEPRECATED,
}
