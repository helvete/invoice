package cz.helvete.invoice.auth;

import cz.helvete.invoice.config.Config;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.mindrot.jbcrypt.BCrypt;

@Stateless
public class SecurityService {

    @Inject
    @Config("bcrypt.rounds")
    private String rounds;

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(Integer.parseInt(rounds)));
    }

    public boolean checkPassword(String password, String bcryptHash) {
        return BCrypt.checkpw(password, bcryptHash);
    }
}
