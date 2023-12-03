package com.jobf.finder.adapters.member.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.jobf.finder.member.model.Authentication;
import com.jobf.finder.member.port.AuthPort;
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

    @Value("${authentication.secret}")
    private String jwtSignAlgorithmSecret;

    @Override
    public Authentication login(MemberLogin memberLogin) {
        Instant now = Instant.now();
        String subject = memberLogin.getEmail();
        String accessToken = createToken(subject, now, now.plus(1, ChronoUnit.DAYS));
        String refreshToken = createToken(subject, now, now.plus(5, ChronoUnit.DAYS));
        return Authentication.builder().tokenType(TOKEN_TYPE).token(accessToken).refreshToken(refreshToken).build();
    }

    private String createToken(String subject, Instant issuedAt, Instant expiresAt) {
        return JWT.create()
                .withSubject(subject)
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .sign(Algorithm.HMAC256(jwtSignAlgorithmSecret.getBytes()));
    }
}
