package com.jobf.finder.adapters;

import com.jobf.finder.member.model.Authentication;
import com.jobf.finder.member.port.AuthPort;
import com.jobf.finder.member.usecase.MemberLogin;

public class FakeAuthPort implements AuthPort {

    public static final String SECRET_KEY = "secret";
    public static final String REFRESH_ADDITION = "refresh";
    public static final String TOKEN_TYPE = "Bearer";

    @Override
    public Authentication login(MemberLogin memberLogin) {
        String password = memberLogin.getPassword();
        return Authentication.builder().tokenType(TOKEN_TYPE).token(password + "." + SECRET_KEY).refreshToken(password + "." + SECRET_KEY + "." + REFRESH_ADDITION).build();
    }
}
