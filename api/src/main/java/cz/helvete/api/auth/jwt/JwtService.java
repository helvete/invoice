package cz.helvete.api.auth.jwt;

import cz.helvete.api.auth.jwt.entity.ClaimsEntity;
import cz.helvete.api.auth.jwt.entity.ParserResultEnum;
import cz.helvete.api.auth.jwt.entity.TokenEntity;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Base64;
import java.security.Key;
import java.util.logging.Logger;
import java.lang.IllegalArgumentException;
import java.time.LocalDateTime;
import javax.inject.Inject;
import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.RequiredTypeException;

import cz.helvete.api.config.Config;

public class JwtService {

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

    private String getJws(TokenEntity claims) {
        logger.info(String.format(
                    "CPAS: encode token for [%s]:%s @%s",
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
                        "CPAS: decode success for [%s]:%s @%s",
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
                    "CPAS: decode failure: [%s] @%s token: %s",
                    environment,
                    LocalDateTime.now(),
                    token),
                e);
    }
}
