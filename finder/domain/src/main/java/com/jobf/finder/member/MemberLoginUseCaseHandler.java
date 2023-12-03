package com.jobf.finder.member;

import com.jobf.finder.common.DomainComponent;
import com.jobf.finder.common.exception.FinderAppDataNotFoundException;
import com.jobf.finder.common.usecase.UseCaseHandler;
import com.jobf.finder.member.model.Authentication;
import com.jobf.finder.member.port.AuthPort;
import com.jobf.finder.member.port.MemberPort;
import com.jobf.finder.member.port.SecurityPort;
import com.jobf.finder.member.usecase.MemberLogin;
import com.jobf.finder.member.usecase.MemberRetrieve;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@DomainComponent
public class MemberLoginUseCaseHandler implements UseCaseHandler<Authentication, MemberLogin> {

    private final AuthPort authPort;
    private final MemberPort memberPort;
    private final SecurityPort securityPort;

    @Override
    public Authentication handle(MemberLogin useCase) {
        var member = memberPort.retrieve(MemberRetrieve.builder().email(useCase.getEmail()).build());
        validatePassword(useCase.getPassword(), member.getPassword());
        return authPort.login(useCase);
    }

    private void validatePassword(String actual, String encrypted) {
        if(!securityPort.validate(actual, encrypted)) {
            throw new FinderAppDataNotFoundException("jobf.finder.member.wrongPassword");
        }
    }
}
