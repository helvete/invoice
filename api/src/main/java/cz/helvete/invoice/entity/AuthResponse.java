package cz.helvete.invoice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.helvete.invoice.db.entity.BaseEntity;

public class AuthResponse extends BaseEntity {
    private String jwt;

    public AuthResponse(String jwt) {
        this.jwt = jwt;
    }

    @JsonIgnore
    public Integer getId() {
        return -1;
    }
    public String getJwt() {
        return jwt;
    }
}
