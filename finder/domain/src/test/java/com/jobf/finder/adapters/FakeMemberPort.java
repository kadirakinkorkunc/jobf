package com.jobf.finder.adapters;

import com.jobf.finder.common.exception.FinderAppDataNotFoundException;
import com.jobf.finder.member.model.Member;
import com.jobf.finder.member.port.MemberPort;
import com.jobf.finder.member.usecase.MemberCreate;
import com.jobf.finder.member.usecase.MemberRetrieve;

import java.util.random.RandomGenerator;

public class FakeMemberPort implements MemberPort {

    public static final Long NOT_FOUND_CASE_MEMBER_ID = 111L;
    public static final String NOT_FOUND_CASE_EMAIL = "notfound@email.com";
    public static final Long FOUND_BY_EMAIL_ID = 222L;

    @Override
    public Member retrieve(MemberRetrieve memberRetrieve) {
        if (NOT_FOUND_CASE_MEMBER_ID.equals(memberRetrieve.getId()) || NOT_FOUND_CASE_EMAIL.equals(memberRetrieve.getEmail())) {
            throw new FinderAppDataNotFoundException("jobf.finder.member.notFound");
        }

        if (memberRetrieve.getEmail() != null) {
            return Member.builder().id(FOUND_BY_EMAIL_ID).email("jobf.finder.test@gmail.com").password("testPassword").build();
        }

        return Member.builder().id(memberRetrieve.getId()).email("jobf.finder.test@gmail.com").password("testPassword").build();
    }

    @Override
    public Member create(MemberCreate member) {
        return Member.builder().id(RandomGenerator.getDefault().nextLong()).email(member.getEmail()).password(member.getPassword()).build();
    }
}
