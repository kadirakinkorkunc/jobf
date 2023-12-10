package com.jobf.finder.member;

import com.jobf.finder.member.adapter.FakeAuthPort;
import com.jobf.finder.member.adapter.FakeMemberPort;
import com.jobf.finder.member.adapter.FakeSecurityPort;
import com.jobf.finder.common.exception.FinderAppDataNotFoundException;
import com.jobf.finder.member.model.Authentication;
import com.jobf.finder.member.usecase.MemberLogin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class MemberLoginTest {

    MemberLoginUseCaseHandler memberLoginUseCaseHandler;

    @BeforeEach
    void setup() {
        memberLoginUseCaseHandler = new MemberLoginUseCaseHandler(new FakeAuthPort(), new FakeMemberPort(), new FakeSecurityPort());
    }

    @Test
    public void shouldLogin() {
        MemberLogin loginUseCase = MemberLogin.builder().email("found@email.com").password("password").build();
        Authentication authentication = memberLoginUseCaseHandler.handle(loginUseCase);
        Assertions.assertNotNull(authentication);
        Assertions.assertEquals(FakeAuthPort.TOKEN_TYPE, authentication.getTokenType());
        Assertions.assertEquals("password" + "." + FakeAuthPort.SECRET_KEY, authentication.getToken());
        Assertions.assertEquals("password" + "." + FakeAuthPort.SECRET_KEY + "." + FakeAuthPort.REFRESH_ADDITION, authentication.getRefreshToken());
    }

    @Test
    public void shouldNotLoginBecauseUserNotFound() {
        MemberLogin loginUseCase = MemberLogin.builder().email(FakeMemberPort.NOT_FOUND_CASE_EMAIL).password("password").build();

        assertThatExceptionOfType(FinderAppDataNotFoundException.class)
                .isThrownBy(() -> memberLoginUseCaseHandler.handle(loginUseCase))
                .withMessage("jobf.finder.member.notFound");
    }

    @Test
    public void shouldNotLoginBecauseWrongPassword() {
        MemberLogin loginUseCase = MemberLogin.builder().email("found@email.com").password(FakeSecurityPort.INVALID_ACTUAL_PASSWORD).build();

        assertThatExceptionOfType(FinderAppDataNotFoundException.class)
                .isThrownBy(() -> memberLoginUseCaseHandler.handle(loginUseCase))
                .withMessage("jobf.finder.member.wrongPassword");
    }
}
