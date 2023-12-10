package com.jobf.finder.adapters.member.security.config;

import com.jobf.finder.adapters.member.security.RequestValidation;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@Profile("!test")
public class SecurityFilterChainConfig {

    private final RequestValidation requestValidation;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeHttpRequests(authorize ->
                    authorize
                            .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                            .requestMatchers(antMatcher("/api/v1/auth/login")).permitAll()
                            .requestMatchers(antMatcher(HttpMethod.POST, "/api/v1/members")).permitAll()
                            .anyRequest().authenticated()
                )
                .headers(headersConfigurer ->  headersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .addFilterBefore(requestValidation, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
