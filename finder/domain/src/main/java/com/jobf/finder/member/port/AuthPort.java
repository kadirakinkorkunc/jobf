package com.jobf.finder.member.port;

import com.jobf.finder.member.model.Authentication;
import com.jobf.finder.member.usecase.MemberLogin;

public interface AuthPort {

    Authentication login(MemberLogin memberLogin);

    //todo: refresh and isValid
}
