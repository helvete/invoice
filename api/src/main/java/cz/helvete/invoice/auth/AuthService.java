package cz.helvete.invoice.auth;

import cz.helvete.invoice.auth.entity.AuthUser;
import cz.helvete.invoice.auth.jwt.JwtService;
import cz.helvete.invoice.auth.jwt.entity.TokenEntity;
import cz.helvete.invoice.db.AccountDAO;
import cz.helvete.invoice.db.entity.Account;
import cz.helvete.invoice.entity.AuthRequest;
import cz.helvete.invoice.entity.AuthResponse;
import cz.helvete.invoice.rest.AppException;
import cz.helvete.invoice.rest.ResponseResultCode;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class AuthService {

    @Inject
    private SecurityService securityService;

    @Inject
    private JwtService jwtService;

    @Inject
    private AccountDAO accountDAO;

    @Inject
    private AuthUser user;

    public AuthResponse login(AuthRequest request) throws AppException {
        return new AuthResponse(
                jwtService.getJws(
                    getTokenEntity(
                        authenticate(request))));
    }

    private Account authenticate(AuthRequest request) throws AppException {
        if (request == null)
            failSlowly();
        Account acc = accountDAO.findByEmail(request.getEmail());
        if (acc == null)
            failSlowly();
        if (!securityService.checkPassword(request.getPassword(), acc.getPassword())) {
            throw new AppException(ResponseResultCode.INVALID_CREDENTIALS);
        }
        user.setEmail(request.getEmail());
        return acc;
    }

    private void failSlowly() {
        securityService.checkPassword(UUID.randomUUID().toString(),
                "$2a$13$wuHdv3KA1bbDqMSdMSC45OVc9e6/jLz10qg1ws3vUBmN6h1rhTRgq"
        );
        throw new AppException(ResponseResultCode.INVALID_CREDENTIALS);
    }

    private TokenEntity getTokenEntity(Account bearer) {
        return new TokenEntity(
                bearer.getEmail(),
                bearer.getId(),
                jwtService.getValidityMillis());
    }
}
