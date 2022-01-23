package cz.helvete.invoice.auth;

import cz.helvete.invoice.auth.entity.AuthUser;
import cz.helvete.invoice.auth.jwt.JwtService;
import cz.helvete.invoice.auth.jwt.entity.ClaimsEntity;
import cz.helvete.invoice.auth.jwt.entity.ParserResultEnum;
import java.io.IOException;
import java.util.logging.Logger;
import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;

@ApplicationScoped
@PreMatching
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationRequestFilter implements ContainerRequestFilter {

    public static final String JWT_HEADER = "x-jwt-assertion";

    @Inject
    private AuthUser user;

    private static JwtService jwtSvc;

    private static Logger logger = Logger.getLogger("AuthenticationRequestFilter");

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authHeader = requestContext.getHeaderString(JWT_HEADER);

        if (!verifyToken(authHeader)) {
            logger.info("JWT token invalid: 401");
            requestContext.abortWith(Response.status(401).build());
        }
    }

    private boolean verifyToken(String jwt) {
        if (jwtSvc == null) {
            initJwtSvc();
        }
        logger.info("JWS:" + jwt);

        // utter gibberish
        if (jwt == null || jwt.isEmpty()) {
            return false;
        }
        // expired, invalid, spoofed, etc
        ClaimsEntity token = jwtSvc.readJws(jwt);
        if (token.getResult() != ParserResultEnum.OK) {
            return false;
        }
        user.setEmail(token.getEmail());
        user.setAccountId(token.getAccountId());
        return true;
    }

    private static void initJwtSvc() {
        jwtSvc = CDI.current().select(JwtService.class).get();
    }
}
