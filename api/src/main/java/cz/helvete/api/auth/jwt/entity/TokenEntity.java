package cz.helvete.api.auth.jwt.entity;

import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.lang.System;

import io.jsonwebtoken.Claims;
import org.apache.commons.codec.digest.DigestUtils;

public class TokenEntity {

    public static final String ISS_CLP_AS = "invoiceapp";
    public static final String AUD_CLIENT = "client";

    private String iss;
    private Integer sub;
    private String subEmail;
    private String aud;
    private long exp;
    private long nbf;
    private long iat;
    private String jti;

    public TokenEntity() {}

    public TokenEntity(String email, Integer id, Long validity) {
        this.sub = id;
        this.subEmail = email;
        long nowUtc = System.currentTimeMillis();
        jti = DigestUtils.sha256Hex(nowUtc + String.valueOf(id));
        iss = TokenEntity.ISS_CLP_AS;
        aud = TokenEntity.AUD_CLIENT;
        iat = nowUtc;
        exp = nowUtc + validity;
        nbf = nowUtc - 1000;
    }

    public Integer getSub() {
        return sub;
    }
    public String getSubEmail() {
        return subEmail;
    }
    public TokenEntity setIss(String iss) {
        this.iss = iss;
        return this;
    }
    public TokenEntity setSub(Integer sub) {
        this.sub = sub;
        return this;
    }
    public TokenEntity setAud(String aud) {
        this.aud = aud;
        return this;
    }
    public TokenEntity setExp(long exp) {
        this.exp = exp;
        return this;
    }
    public TokenEntity setNbf(long nbf) {
        this.nbf = nbf;
        return this;
    }
    public TokenEntity setIat(long iat) {
        this.iat = iat;
        return this;
    }
    public TokenEntity setJti(String jti) {
        this.jti = jti;
        return this;
    }
    public TokenEntity setSubEmail(String email) {
        subEmail = email;
        return this;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> token = new HashMap<String, Object>();
        token.put(Claims.ISSUER, iss);
        token.put(Claims.SUBJECT, sub);
        token.put(ClaimsEntity.SUB_EML, subEmail);
        token.put(Claims.AUDIENCE, aud);
        token.put(Claims.ID, jti);
        token.put(Claims.EXPIRATION, new Date(exp));
        token.put(Claims.ISSUED_AT, new Date(iat));
        token.put(Claims.NOT_BEFORE, new Date(nbf));

        return token;
    }
}
