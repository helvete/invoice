package cz.helvete.invoice.auth.jwt;

import cz.helvete.invoice.auth.jwt.entity.ClaimsEntity;
import cz.helvete.invoice.auth.jwt.entity.ParserResultEnum;
import cz.helvete.invoice.auth.jwt.entity.TokenEntity;
import cz.helvete.invoice.config.Config;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.RequiredTypeException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.lang.IllegalArgumentException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import javax.inject.Inject;

public class JwtService {

    public static final String JWT_HEADER = "x-jwt-assertion";

    @Inject
    @Config("jwt.algorithm")
    private String algo;

    @Inject
    @Config("jwt.keyString")
    private String base64Key;

    @Inject
    @Config("environment")
    private String environment;

    @Inject
    @Config("jwt.validityMillis")
    private String validityMillis;

    @Inject
    private Logger logger;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64Key));
    }

    private SignatureAlgorithm getAlgo() {
        return SignatureAlgorithm.forName(algo);
    }

    public String getJws(String email, Integer id) {
        Long validity = Long.parseLong(validityMillis);
        return getJws(new TokenEntity(email, id, validity));
    }

    public String getJws(TokenEntity claims) {
        logger.info(String.format(
                    "JWT: encode token for [%s]:%s @%s",
                    environment,
                    claims.getSub(),
                    LocalDateTime.now()));
        return Jwts.builder()
            .setHeaderParam("kid", environment)
            .setClaims(claims.toMap())
            .signWith(getKey(), getAlgo())
            .compact();
    }

    public ClaimsEntity readJws(String jwsString) {
        Claims token;
        try {
            token = Jwts.parser()
                .setAllowedClockSkewSeconds(3 * 60)
                .setSigningKey(getKey())
                .parseClaimsJws(jwsString)
                .getBody();
        } catch (ExpiredJwtException e) {
            logFailure(jwsString, e);
            return new ClaimsEntity(ParserResultEnum.TOKEN_EXPIRED);
        } catch (UnsupportedJwtException | MalformedJwtException e) {
            logFailure(jwsString, e);
            return new ClaimsEntity(ParserResultEnum.TOKEN_MALFORMED);
        } catch (SignatureException e) {
            logFailure(jwsString, e);
            return new ClaimsEntity(ParserResultEnum.SIGNATURE_INVALID);
        } catch (IllegalArgumentException e) {
            logFailure(jwsString, e);
            return new ClaimsEntity(ParserResultEnum.NOT_A_TOKEN);
        }
        try {
            logger.info(String.format(
                        "JWT: decode success for [%s]:%s @%s",
                        environment,
                        token.get(Claims.SUBJECT, Integer.class),
                        LocalDateTime.now()));
            return new ClaimsEntity(token);
        } catch (RequiredTypeException e) {
            logFailure(jwsString, e);
            return new ClaimsEntity(ParserResultEnum.TOKEN_DEPRECATED);
        }
    }

    private void logFailure(String token, Exception e) {
        logger.log(
                Level.WARNING,
                String.format(
                    "JWT: decode failure: [%s] @%s token: %s",
                    environment,
                    LocalDateTime.now(),
                    token),
                e);
    }

    public Long getValidityMillis() {
        return Long.parseLong(validityMillis);
    }
}
