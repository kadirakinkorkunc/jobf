package com.jobf.finder.member;

import com.jobf.finder.common.DomainComponent;
import com.jobf.finder.common.usecase.UseCaseHandler;
import com.jobf.finder.member.model.Member;
import com.jobf.finder.member.port.MemberPort;
import com.jobf.finder.member.usecase.MemberRetrieve;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@DomainComponent
public class MemberRetrieveUseCaseHandler implements UseCaseHandler<Member, MemberRetrieve> {

    private final MemberPort memberPort;

    @Override
    public Member handle(MemberRetrieve memberRetrieve) {
        return memberPort.retrieve(memberRetrieve);
    }
}
