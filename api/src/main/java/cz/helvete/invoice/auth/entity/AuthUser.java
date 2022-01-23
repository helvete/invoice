package cz.helvete.invoice.auth.entity;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Path;

@Path("authenticated-user-bean")
@RequestScoped
public class AuthUser {

    protected String email;
    protected Integer accountId;

    public String getEmail() {
        return email;
    }
    public AuthUser setEmail(String email) {
        this.email = email;
        return this;
    }
    public Integer getAccountId() {
        return accountId;
    }
    public AuthUser setAccountId(Integer accountId) {
        this.accountId = accountId;
        return this;
    }
}
