package com.jobf.finder.adapters.member.security;

import com.jobf.finder.common.usecase.UseCaseHandler;
import com.jobf.finder.member.model.Member;
import com.jobf.finder.member.usecase.MemberRetrieve;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.DispatcherTypeRequestMatcher;

import java.util.Collections;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class Config {

    private final UseCaseHandler<Member, MemberRetrieve> memberRetrieveUseCaseHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Member member = memberRetrieveUseCaseHandler.handle(MemberRetrieve.builder().email(username).build());
            return new User(username, member.getPassword(), Collections.emptyList());
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeHttpRequests(authorize ->
                    authorize
                            .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                            .requestMatchers(antMatcher("/api/v1/auth/login")).permitAll()
                            .requestMatchers(antMatcher(HttpMethod.POST, "/api/v1/members")).permitAll()
                            .requestMatchers(antMatcher("/h2-console/**")).permitAll()
                )
                .headers(headersConfigurer ->  headersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .addFilterBefore(new RequestValidation(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
