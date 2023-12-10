package com.jobf.finder.member;


import com.jobf.finder.member.adapter.FakeMemberPort;
import com.jobf.finder.common.exception.FinderAppDataNotFoundException;
import com.jobf.finder.member.model.Member;
import com.jobf.finder.member.usecase.MemberRetrieve;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class MemberRetrieveTest {

    MemberRetrieveUseCaseHandler memberRetrieveUseCaseHandler;

    @BeforeEach
    void setUp() {
        memberRetrieveUseCaseHandler = new MemberRetrieveUseCaseHandler(new FakeMemberPort());
    }

    @Test
    void shouldRetrieveMemberById() {
        // given
        var memberRetrieve = MemberRetrieve.builder().id(3L).build();

        //when
        Member result = memberRetrieveUseCaseHandler.handle(memberRetrieve);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getEmail()).isEqualTo("jobf.finder.test@gmail.com");
        assertThat(result.getPassword()).isEqualTo("testPassword");

    }

    @Test
    void shouldRetrieveMemberByEmail() {
        // given
        var memberRetrieve = MemberRetrieve.builder().email("jobf.finder.test@gmail.com").build();

        //when
        Member result = memberRetrieveUseCaseHandler.handle(memberRetrieve);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getId()).isEqualTo(FakeMemberPort.FOUND_BY_EMAIL_ID);
        assertThat(result.getEmail()).isEqualTo("jobf.finder.test@gmail.com");
        assertThat(result.getPassword()).isEqualTo("testPassword");

    }

    @Test
    void shouldNotRetrieveMember() {
        // given
        var memberRetrieve = MemberRetrieve.builder().id(FakeMemberPort.NOT_FOUND_CASE_MEMBER_ID).build();

        //when
        assertThatExceptionOfType(FinderAppDataNotFoundException.class)
                .isThrownBy(() -> memberRetrieveUseCaseHandler.handle(memberRetrieve))
                .withMessage("jobf.finder.member.notFound");

    }

}
