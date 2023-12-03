package com.jobf.finder.member;

import com.jobf.finder.common.DomainComponent;
import com.jobf.finder.common.usecase.UseCaseHandler;
import com.jobf.finder.member.model.Member;
import com.jobf.finder.member.port.MemberPort;
import com.jobf.finder.member.port.SecurityPort;
import com.jobf.finder.member.usecase.MemberCreate;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@DomainComponent
public class MemberCreateUseCaseHandler implements UseCaseHandler<Member, MemberCreate> {

    private final MemberPort memberPort;
    private final SecurityPort securityPort;

    @Override
    public Member handle(MemberCreate useCase) {
        encryptPassword(useCase);
        return memberPort.create(useCase);
    }

    private void encryptPassword(MemberCreate memberCreate) {
        memberCreate.setPassword(securityPort.encryptText(memberCreate.getPassword()));
    }
}
