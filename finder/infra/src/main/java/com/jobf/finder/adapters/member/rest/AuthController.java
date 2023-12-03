package com.jobf.finder.adapters.member.rest;

import com.jobf.finder.adapters.member.rest.dto.MemberLoginRequest;
import com.jobf.finder.adapters.member.rest.dto.MemberLoginResponse;
import com.jobf.finder.common.usecase.UseCaseHandler;
import com.jobf.finder.member.model.Authentication;
import com.jobf.finder.member.usecase.MemberLogin;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UseCaseHandler<Authentication, MemberLogin> memberLoginUseCaseHandler;

    @PostMapping("login")
    public MemberLoginResponse login(@Valid @RequestBody MemberLoginRequest memberLoginRequest) {
        return MemberLoginResponse.from(memberLoginUseCaseHandler.handle(memberLoginRequest.toUseCase()));
    }
}
