package fr.utc.sr03.chatapp.service;

import java.time.Duration;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;

import fr.utc.sr03.chatapp.model.HttpUserDetails;


@Service
public class JwtTokenService {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenService.class);

    private static final Duration JWT_TOKEN_VALIDITY = Duration.ofMinutes(20);

    private final Algorithm hmac512;
    private final JWTVerifier verifier;

    public JwtTokenService(@Value("${jwt.secret}") final String secret) {
        this.hmac512 = Algorithm.HMAC512(secret);
        this.verifier = JWT.require(this.hmac512).build();
    }

    public String generateToken(final HttpUserDetails userDetails) {
        final Instant now = Instant.now();
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuer("app")
                .withIssuedAt(now)
                .withExpiresAt(now.plusMillis(JWT_TOKEN_VALIDITY.toMillis()))
                .withClaim("id", userDetails.getId())
                .withClaim("firstName", userDetails.getFirstName())
                .withClaim("lastName", userDetails.getLastName())
                .withClaim("isAdmin", userDetails.getIsAdmin())
                .withClaim("isLocked", userDetails.isAccountNonLocked())
                .sign(this.hmac512);
    }

    public String validateTokenAndGetEmail(final String token) {
        try {
            return verifier.verify(token).getSubject();
        } catch (final JWTVerificationException verificationEx) {
            log.warn("Invalid token", verificationEx);
            return null;
        }
    }

}
