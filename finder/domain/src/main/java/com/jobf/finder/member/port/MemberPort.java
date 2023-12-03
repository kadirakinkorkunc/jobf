package com.jobf.finder.member.port;

import com.jobf.finder.member.model.Member;
import com.jobf.finder.member.usecase.MemberCreate;
import com.jobf.finder.member.usecase.MemberRetrieve;

public interface MemberPort {

    Member retrieve(MemberRetrieve memberRetrieve);

    Member create(MemberCreate member);
}
