package com.jobf.finder.adapters.member.rest;

import com.jobf.finder.adapters.member.rest.dto.CreateMemberRequest;
import com.jobf.finder.adapters.member.rest.dto.MemberResponse;
import com.jobf.finder.common.usecase.UseCaseHandler;
import com.jobf.finder.member.model.Member;
import com.jobf.finder.member.usecase.MemberCreate;
import com.jobf.finder.member.usecase.MemberRetrieve;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final UseCaseHandler<Member, MemberCreate> memberCreateUseCaseHandler;
    private final UseCaseHandler<Member, MemberRetrieve> memberGetUseCaseHandler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MemberResponse createMember(@Valid @RequestBody CreateMemberRequest createMemberRequest) {
        return MemberResponse.from(memberCreateUseCaseHandler.handle(createMemberRequest.toUseCase()));
    }

    @GetMapping("/{id}")
    public MemberResponse getMember(@PathVariable Long id) {
        return MemberResponse.from(memberGetUseCaseHandler.handle(MemberRetrieve.builder().id(id).build()));
    }
}
