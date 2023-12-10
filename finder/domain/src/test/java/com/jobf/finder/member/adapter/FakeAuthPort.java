package com.jobf.finder.member.adapter;

import com.jobf.finder.member.model.Authentication;
import com.jobf.finder.member.model.AuthenticationDetail;
import com.jobf.finder.member.port.AuthPort;
import com.jobf.finder.member.usecase.MemberAuthenticate;
import com.jobf.finder.member.usecase.MemberLogin;

public class FakeAuthPort implements AuthPort {

    public static final String SECRET_KEY = "secret";
    public static final String REFRESH_ADDITION = "refresh";
    public static final String TOKEN_TYPE = "Bearer";
    public static final String EXPIRED_JWT = TOKEN_TYPE.concat(" some_expired_jwt");
    public static final String INVALID_JWT = TOKEN_TYPE.concat(" some_invalid_jwt");

    @Override
    public Authentication login(MemberLogin memberLogin) {
        String password = memberLogin.getPassword();
        return Authentication.builder().tokenType(TOKEN_TYPE).token(password + "." + SECRET_KEY).refreshToken(password + "." + SECRET_KEY + "." + REFRESH_ADDITION).build();
    }

    @Override
    public AuthenticationDetail validate(MemberAuthenticate memberAuthenticate) {
        var accessToken = memberAuthenticate.getAccessToken();
        if (EXPIRED_JWT.contentEquals(accessToken) || INVALID_JWT.contentEquals(accessToken)) {
            return AuthenticationDetail.builder().authenticated(false).build();
        }

        return AuthenticationDetail.builder().authenticated(true).subject("subject").build();
    }
}
