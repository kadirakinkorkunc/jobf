package com.jobf.finder.adapters.member.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jobf.finder.member.model.Authentication;
import com.jobf.finder.member.model.AuthenticationDetail;
import com.jobf.finder.member.port.AuthPort;
import com.jobf.finder.member.usecase.MemberAuthenticate;
import com.jobf.finder.member.usecase.MemberLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class AuthDataAdapter implements AuthPort {

    private static final String TOKEN_TYPE = "Bearer";
    private static final String ISSUER = "jobf";

    @Value("${authentication.secret}")
    private String jwtSignAlgorithmSecret;

    @Value("${authentication.accesskey.expiresecond}")
    private Long accessKeyExpire;

    @Value("${authentication.refreshkey.expiresecond}")
    private Long refreshKeyExpire;

    @Override
    public Authentication login(MemberLogin memberLogin) {
        Instant now = Instant.now();
        String subject = memberLogin.getEmail();
        String accessToken = createToken(subject, now, now.plus(accessKeyExpire, ChronoUnit.SECONDS));
        String refreshToken = createToken(subject, now, now.plus(refreshKeyExpire, ChronoUnit.SECONDS));
        return Authentication.builder().tokenType(TOKEN_TYPE).token(accessToken).refreshToken(refreshToken).build();
    }

    @Override
    public AuthenticationDetail validate(MemberAuthenticate memberAuthenticate) {
        try {
            DecodedJWT decodedJWT = JWT.require(getSigningAlgorithm())
                    .withIssuer(ISSUER)
                    .build()
                    .verify(memberAuthenticate.getAccessToken());

            return AuthenticationDetail.builder().authenticated(true).subject(decodedJWT.getSubject()).build();
        } catch (JWTVerificationException verificationException) {
            return AuthenticationDetail.builder().authenticated(false).build();
        }
    }

    private String createToken(String subject, Instant issuedAt, Instant expiresAt) {
        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(subject)
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .sign(getSigningAlgorithm());
    }

    private Algorithm getSigningAlgorithm() {
        return Algorithm.HMAC256(jwtSignAlgorithmSecret.getBytes());
    }
}
