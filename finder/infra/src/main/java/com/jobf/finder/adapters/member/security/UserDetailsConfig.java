package com.jobf.finder.adapters.member.security;


import com.jobf.finder.common.usecase.UseCaseHandler;
import com.jobf.finder.member.model.Member;
import com.jobf.finder.member.usecase.MemberRetrieve;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class UserDetailsConfig {

    private final UseCaseHandler<Member, MemberRetrieve> memberRetrieveUseCaseHandler;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Member member = memberRetrieveUseCaseHandler.handle(MemberRetrieve.builder().email(username).build());
            return new User(username, member.getPassword(), Collections.emptyList());
        };
    }
}
