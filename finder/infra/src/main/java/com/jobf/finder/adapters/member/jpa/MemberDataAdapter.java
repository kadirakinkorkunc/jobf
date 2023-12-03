package com.jobf.finder.adapters.member.jpa;

import com.jobf.finder.adapters.member.jpa.entity.MemberEntity;
import com.jobf.finder.adapters.member.jpa.repository.MemberJpaRepository;
import com.jobf.finder.common.exception.FinderAppDataNotFoundException;
import com.jobf.finder.member.model.Member;
import com.jobf.finder.member.port.MemberPort;
import com.jobf.finder.member.usecase.MemberCreate;
import com.jobf.finder.member.usecase.MemberRetrieve;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDataAdapter implements MemberPort {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Member retrieve(MemberRetrieve memberRetrieve) {
        if (memberRetrieve.getEmail() != null) {
            return memberJpaRepository.findByEmail(memberRetrieve.getEmail()).map(MemberEntity::toModel).orElseThrow(() -> new FinderAppDataNotFoundException("jobf.finder.member.notFound"));
        }
        return memberJpaRepository.findById(memberRetrieve.getId()).map(MemberEntity::toModel).orElseThrow(() -> new FinderAppDataNotFoundException("jobf.finder.member.notFound"));
    }

    @Override
    public Member create(MemberCreate memberCreate) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setEmail(memberCreate.getEmail());
        memberEntity.setPassword(memberCreate.getPassword());
        return memberJpaRepository.save(memberEntity).toModel();
    }
}
