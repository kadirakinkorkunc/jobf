package com.jobf.finder.adapters.member.security;

import com.jobf.finder.common.usecase.UseCaseHandler;
import com.jobf.finder.member.model.AuthenticationDetail;
import com.jobf.finder.member.usecase.MemberAuthenticate;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
@Component
public class RequestValidation extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final UseCaseHandler<AuthenticationDetail, MemberAuthenticate> memberAuthenticateUseCaseHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ") || SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        var accessToken = authHeader.substring(authHeader.indexOf(" ") + 1);
        AuthenticationDetail authenticationDetail = memberAuthenticateUseCaseHandler.handle(MemberAuthenticate.builder().accessToken(accessToken).build());
        if (authenticationDetail.isAuthenticated()) {
            var username = authenticationDetail.getSubject();
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            var authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }
}
