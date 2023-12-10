package com.jobf.finder.integration.member.security;

import com.jobf.finder.ITComponent;
import com.jobf.finder.adapters.member.security.AuthDataAdapter;
import com.jobf.finder.member.model.Authentication;
import com.jobf.finder.member.model.AuthenticationDetail;
import com.jobf.finder.member.usecase.MemberAuthenticate;
import com.jobf.finder.member.usecase.MemberLogin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

@ITComponent
public class AuthDataAdapterIT {


    @Autowired
    AuthDataAdapter authDataAdapter;

    @Test
    void shouldLogin() {
        // given
        var memberLogin = MemberLogin.builder().email("test1@email.com").password("pass1").build();

        // when
        Authentication authentication = authDataAdapter.login(memberLogin);

        // then
        assertThat(authentication).isNotNull();
        assertThat(authentication.getTokenType()).isNotNull();
        assertThat(authentication.getToken()).isNotNull();
        assertThat(authentication.getRefreshToken()).isNotNull();
    }

    @Test
    void shouldValidate() {
        // given
        String mail = "test1@email.com";
        var memberLogin = MemberLogin.builder().email(mail).password("pass1").build();
        Authentication authentication = authDataAdapter.login(memberLogin);
        MemberAuthenticate memberAuthenticate = MemberAuthenticate.builder().accessToken(authentication.getToken()).build();

        // when
        AuthenticationDetail authenticationDetail = authDataAdapter.validate(memberAuthenticate);

        // then
        assertThat(authenticationDetail).isNotNull();
        assertThat(authenticationDetail.isAuthenticated()).isTrue();
        assertThat(authenticationDetail.getSubject()).isEqualTo(mail);
    }

    @Test
    void shouldNotValidateBecauseInvalidToken() {
        // given
        MemberAuthenticate memberAuthenticate = MemberAuthenticate.builder().accessToken("invalid_token").build();

        // when
        AuthenticationDetail authenticationDetail = authDataAdapter.validate(memberAuthenticate);

        // then
        assertThat(authenticationDetail).isNotNull();
        assertThat(authenticationDetail.isAuthenticated()).isFalse();
        assertThat(authenticationDetail.getSubject()).isNull();
    }

    @Test
    void shouldNotValidateBecauseTokenIsExpired() throws InterruptedException {
        // given
        var memberLogin = MemberLogin.builder().email("test1@email.com").password("pass1").build();
        Authentication authentication = authDataAdapter.login(memberLogin);
        MemberAuthenticate memberAuthenticate = MemberAuthenticate.builder().accessToken(authentication.getToken()).build();

        // when
        Thread.sleep(Duration.of(3, ChronoUnit.SECONDS)); // 2 is the real expire second, we are giving 3-second sleep to test the case
        AuthenticationDetail authenticationDetail = authDataAdapter.validate(memberAuthenticate);

        // then
        assertThat(authenticationDetail).isNotNull();
        assertThat(authenticationDetail.isAuthenticated()).isFalse();
        assertThat(authenticationDetail.getSubject()).isNull();
    }
}
