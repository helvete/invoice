package cz.helvete.invoice.auth.jwt.entity;

import java.util.Date;
import io.jsonwebtoken.Claims;

public class ClaimsEntity {

    public static final String SUB_EML = "subEmail";

    private ParserResultEnum result;
    private String email;
    private String audience;
    private String tokenId;
    private Date issuedAt;
    private Integer accountId;

    public ClaimsEntity(ParserResultEnum result) {
        this.result = result;
    }
    public ClaimsEntity(Claims jwsData) {
        this.result = ParserResultEnum.OK;
        this.email = jwsData.get(SUB_EML, String.class);
        this.audience = jwsData.get(Claims.AUDIENCE, String.class);
        this.tokenId = jwsData.get(Claims.ID, String.class);
        this.issuedAt = jwsData.get(Claims.ISSUED_AT, Date.class);
        this.accountId = jwsData.get(Claims.SUBJECT, Integer.class);
    }

    public ParserResultEnum getResult() {
        return result;
    }
    public String getEmail() {
        return email;
    }
    public String getAudience() {
        return audience;
    }
    public Date getIssuedAt() {
        return issuedAt;
    }
    public String getTokenId() {
        return tokenId;
    }
    public Integer getAccountId() {
        return accountId;
    }
}
