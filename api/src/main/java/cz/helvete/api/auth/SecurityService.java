package cz.helvete.api.auth;

import cz.helvete.api.config.Config;
import org.mindrot.jbcrypt.BCrypt;
import javax.inject.Inject;
import javax.ejb.Stateless;

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
