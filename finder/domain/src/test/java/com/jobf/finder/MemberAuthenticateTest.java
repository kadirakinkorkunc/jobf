package com.jobf.finder;

import com.jobf.finder.adapters.FakeAuthPort;
import com.jobf.finder.member.MemberAuthenticateUseCaseHandler;
import com.jobf.finder.member.usecase.MemberAuthenticate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberAuthenticateTest {

    MemberAuthenticateUseCaseHandler memberAuthenticateUseCaseHandler;

    @BeforeEach
    void setUp() {
        memberAuthenticateUseCaseHandler = new MemberAuthenticateUseCaseHandler(new FakeAuthPort());
    }

    @Test
    void shouldAuthenticate() {
        var authenticationDetail = memberAuthenticateUseCaseHandler.handle(MemberAuthenticate.builder().accessToken("validAccessToken").build());
        assertThat(authenticationDetail.isAuthenticated()).isEqualTo(true);
        assertThat(authenticationDetail.getSubject()).isNotNull();
    }

    @Test
    void shouldNotAuthenticateBecauseOfExpiredToken() {
        var authenticationDetail = memberAuthenticateUseCaseHandler.handle(MemberAuthenticate.builder().accessToken(FakeAuthPort.EXPIRED_JWT).build());
        assertThat(authenticationDetail.isAuthenticated()).isEqualTo(false);
        assertThat(authenticationDetail.getSubject()).isNull();
    }

    @Test
    void shouldNotAuthenticateBecauseOfInvalidToken() {
        var authenticationDetail = memberAuthenticateUseCaseHandler.handle(MemberAuthenticate.builder().accessToken(FakeAuthPort.INVALID_JWT).build());
        assertThat(authenticationDetail.isAuthenticated()).isEqualTo(false);
        assertThat(authenticationDetail.getSubject()).isNull();
    }
}
