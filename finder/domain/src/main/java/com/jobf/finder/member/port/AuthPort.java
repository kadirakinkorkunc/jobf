package com.jobf.finder.member.port;

import com.jobf.finder.member.model.Authentication;
import com.jobf.finder.member.model.AuthenticationDetail;
import com.jobf.finder.member.usecase.MemberAuthenticate;
import com.jobf.finder.member.usecase.MemberLogin;

public interface AuthPort {

    Authentication login(MemberLogin memberLogin);

    AuthenticationDetail validate(MemberAuthenticate memberAuthenticate);

    //todo: refresh
}
