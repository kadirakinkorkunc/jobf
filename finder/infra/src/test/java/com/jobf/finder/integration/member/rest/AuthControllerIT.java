package com.jobf.finder.integration.member.rest;

import com.jobf.finder.BaseIT;
import com.jobf.finder.ITComponent;
import com.jobf.finder.adapters.member.rest.dto.MemberLoginRequest;
import com.jobf.finder.adapters.member.rest.dto.MemberLoginResponse;
import com.jobf.finder.common.rest.dto.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.from;

@ITComponent
@Sql(scripts = "classpath:sql/members.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AuthControllerIT extends BaseIT {

    private final ParameterizedTypeReference<MemberLoginResponse> memberLoginResponseParameterizedTypeReference = new ParameterizedTypeReference<MemberLoginResponse>() {

    };
    private final ParameterizedTypeReference<ErrorResponse> errorResponseParameterizedTypeReference = new ParameterizedTypeReference<>() {
    };

    @Test
    void shouldLogin() {
        // given
        var memberLogin = new MemberLoginRequest();
        memberLogin.setEmail("test1@email.com"); // reference to resources/sql/members.sql
        memberLogin.setPassword("someb2cryptpassword1");

        // when
        ResponseEntity<MemberLoginResponse> exchanged = restTemplate.exchange("/api/v1/auth/login",
                HttpMethod.POST,
                new HttpEntity<>(memberLogin),
                memberLoginResponseParameterizedTypeReference
        );

        // then - response
        assertThat(exchanged)
                .isNotNull()
                .returns(HttpStatus.OK, from(ResponseEntity::getStatusCode));

        // then - data
        assertThat(exchanged.getBody()).isNotNull();
        assertThat(exchanged.getBody().getTokenType()).isNotNull();
        assertThat(exchanged.getBody().getAccessToken()).isNotNull();
        assertThat(exchanged.getBody().getRefreshToken()).isNotNull();
    }

    @Test
    void shouldNotLoginWrongPassword() {
        // given
        var memberLogin = new MemberLoginRequest();
        memberLogin.setEmail("test1@email.com"); // reference to resources/sql/members.sql
        memberLogin.setPassword("wrongpassword");

        // when
        ResponseEntity<ErrorResponse> exchanged = restTemplate.exchange("/api/v1/auth/login",
                HttpMethod.POST,
                new HttpEntity<>(memberLogin),
                errorResponseParameterizedTypeReference
        );

        // then - response
        assertThat(exchanged)
                .isNotNull()
                .returns(HttpStatus.NOT_FOUND, from(ResponseEntity::getStatusCode));

        // then - data
        assertThat(exchanged.getBody()).isNotNull();
        assertThat(exchanged.getBody().getCode()).isEqualTo("2");
    }

    @Test
    void shouldNotLoginNoAccount() {
        // given
        var memberLogin = new MemberLoginRequest();
        memberLogin.setEmail("test1321312@email.com"); // reference to resources/sql/members.sql
        memberLogin.setPassword("wrongpassword");

        // when
        ResponseEntity<ErrorResponse> exchanged = restTemplate.exchange("/api/v1/auth/login",
                HttpMethod.POST,
                new HttpEntity<>(memberLogin),
                errorResponseParameterizedTypeReference
        );

        // then - response
        assertThat(exchanged)
                .isNotNull()
                .returns(HttpStatus.NOT_FOUND, from(ResponseEntity::getStatusCode));

        // then - data
        assertThat(exchanged.getBody()).isNotNull();
        assertThat(exchanged.getBody().getCode()).isEqualTo("1");
    }

}
