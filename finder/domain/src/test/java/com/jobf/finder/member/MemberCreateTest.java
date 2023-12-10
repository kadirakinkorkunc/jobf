package com.jobf.finder.member;


import com.jobf.finder.member.adapter.FakeMemberPort;
import com.jobf.finder.member.adapter.FakeSecurityPort;
import com.jobf.finder.member.model.Member;
import com.jobf.finder.member.usecase.MemberCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberCreateTest {

    MemberCreateUseCaseHandler memberCreateUseCaseHandler;

    @BeforeEach
    void setUp() {
        memberCreateUseCaseHandler = new MemberCreateUseCaseHandler(new FakeMemberPort(), new FakeSecurityPort());
    }

    @Test
    void shouldCreateMember() {
        // given
        String initialPassword = "textPassword1";
        MemberCreate testMember = MemberCreate.builder()
                .email("test.email@gmail.com")
                .password(initialPassword)
                .build();

        //when
        Member result = memberCreateUseCaseHandler.handle(testMember);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getEmail()).isEqualTo(testMember.getEmail());
        assertThat(result.getPassword()).isEqualTo(initialPassword + ".encoded");

    }

}
