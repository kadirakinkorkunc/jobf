package com.jobf.finder.integration.member.rest;

import com.jobf.finder.BaseIT;
import com.jobf.finder.ITComponent;
import com.jobf.finder.adapters.member.rest.dto.CreateMemberRequest;
import com.jobf.finder.adapters.member.rest.dto.MemberResponse;
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
public class MemberControllerIT extends BaseIT {

    private final ParameterizedTypeReference<MemberResponse> parameterizedTypeReference = new ParameterizedTypeReference<>() {
    };

    private final ParameterizedTypeReference<ErrorResponse> errorResponseParameterizedTypeReference = new ParameterizedTypeReference<>() {
    };

    @Test
    void shouldCreateMember() {
        // given
        String mail = "email21321@email.com";
        var createMember = new CreateMemberRequest(mail, "password333");

        // when
        ResponseEntity<MemberResponse> exchanged = restTemplate.exchange("/api/v1/members",
                HttpMethod.POST,
                new HttpEntity<>(createMember),
                parameterizedTypeReference
        );

        // then - response
        assertThat(exchanged).isNotNull()
                .returns(HttpStatus.CREATED, from(ResponseEntity::getStatusCode));

        // then - data
        assertThat(exchanged.getBody()).isNotNull();
        assertThat(exchanged.getBody().getEmail()).isEqualTo(mail);
    }

    @Test
    void shouldGetMember() {
        // given
        long memberId = 1;

        // when
        var responseEntity = restTemplate.exchange("/api/v1/members/" + memberId,
                HttpMethod.GET,
                new HttpEntity<>(null, null),
                parameterizedTypeReference
        );

        // then - assert response
        assertThat(responseEntity).isNotNull()
                .returns(HttpStatus.OK, from(ResponseEntity::getStatusCode));

        // then - assert data
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getEmail()).isEqualTo("test1@email.com");

    }

    @Test
    void shouldNotGetMemberBecauseNotFound() {
        // given
        long memberId = 111;

        // when
        var responseEntity = restTemplate.exchange("/api/v1/members/" + memberId,
                HttpMethod.GET,
                new HttpEntity<>(null, null),
                errorResponseParameterizedTypeReference
        );

        // then - assert response
        assertThat(responseEntity).isNotNull()
                .returns(HttpStatus.NOT_FOUND, from(ResponseEntity::getStatusCode));

        // then - assert data
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getCode()).isEqualTo("1");

    }
}
