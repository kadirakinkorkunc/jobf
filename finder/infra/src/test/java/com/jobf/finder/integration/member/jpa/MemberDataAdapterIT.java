package com.jobf.finder.integration.member.jpa;

import com.jobf.finder.BaseIT;
import com.jobf.finder.ITComponent;
import com.jobf.finder.adapters.member.jpa.MemberDataAdapter;
import com.jobf.finder.adapters.member.jpa.repository.MemberJpaRepository;
import com.jobf.finder.common.exception.FinderAppDataNotFoundException;
import com.jobf.finder.member.usecase.MemberCreate;
import com.jobf.finder.member.usecase.MemberRetrieve;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


@ITComponent
@Sql(scripts = "classpath:sql/members.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class MemberDataAdapterIT extends BaseIT {

    @Autowired
    MemberDataAdapter memberDataAdapter;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    void shouldCreateMember() {
        // given
        var createMember = MemberCreate.builder().email("test4@email.com").password("password4").build();

        // when
        var createdMember = memberDataAdapter.create(createMember);

        // then
        var member = memberJpaRepository.findById(4L);
        assertThat(member).isPresent();
        assertThat(member.get().toModel()).isEqualTo(createdMember);
    }

    @Test
    void shouldRetrieveMemberViaEmail() {
        // given
        var retrieveMember = MemberRetrieve.builder().email("test1@email.com").build();

        // when
        var retrievedMember = memberDataAdapter.retrieve(retrieveMember);

        // then
        assertThat(retrievedMember).isNotNull();
        assertThat(retrievedMember.getId()).isEqualTo(1);
        assertThat(retrievedMember.getEmail()).isEqualTo("test1@email.com");
        assertThat(retrievedMember.getPassword()).isEqualTo("someb2cryptpassword1");
    }

    @Test
    void shouldRetrieveMemberViaId() {
        // given
        var retrieveMember = MemberRetrieve.builder().id(1L).build();

        // when
        var retrievedMember = memberDataAdapter.retrieve(retrieveMember);

        // then
        assertThat(retrievedMember).isNotNull();
        assertThat(retrievedMember.getId()).isEqualTo(1);
        assertThat(retrievedMember.getEmail()).isEqualTo("test1@email.com");
        assertThat(retrievedMember.getPassword()).isEqualTo("someb2cryptpassword1");
    }

    @Test
    void shouldNotRetrieveMemberBecauseEmailDoesNotExists() {
        // given
        var retrieveMember = MemberRetrieve.builder().email("notexisting@email.com").build();

        // when - then
        assertThatExceptionOfType(FinderAppDataNotFoundException.class)
                .isThrownBy(() -> memberDataAdapter.retrieve(retrieveMember))
                .withMessage("jobf.finder.member.notFound");
    }

    @Test
    void shouldNotRetrieveMemberBecauseIdDoesNotExists() {
        // given
        var retrieveMember = MemberRetrieve.builder().id(15L).build();

        // when - then
        assertThatExceptionOfType(FinderAppDataNotFoundException.class)
                .isThrownBy(() -> memberDataAdapter.retrieve(retrieveMember))
                .withMessage("jobf.finder.member.notFound");
    }
}
