package com.jobf.finder.member;

import com.jobf.finder.common.DomainComponent;
import com.jobf.finder.common.usecase.UseCaseHandler;
import com.jobf.finder.member.model.AuthenticationDetail;
import com.jobf.finder.member.port.AuthPort;
import com.jobf.finder.member.usecase.MemberAuthenticate;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@DomainComponent
public class MemberAuthenticateUseCaseHandler implements UseCaseHandler<AuthenticationDetail, MemberAuthenticate> {

    private final AuthPort authPort;

    @Override
    public AuthenticationDetail handle(MemberAuthenticate useCase) {
        return authPort.validate(useCase);
    }

}
