package com.jobf.finder.job.posting;

import com.jobf.finder.job.posting.adapters.FakeJobPostingPort;
import com.jobf.finder.job.posting.usecase.JobPostingCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JobPostingCreateTest {

    JobPostingCreateUseCaseHandler jobPostingCreateUseCaseHandler;

    @BeforeEach
    void setUp() {
        jobPostingCreateUseCaseHandler = new JobPostingCreateUseCaseHandler(new FakeJobPostingPort());
    }

    @Test
    void shouldCreate() {
        // given
        var create = JobPostingCreate.builder()
                .title("title")
                .description("description")
                .ownerId(1L)
                .build();

        // when
        var response = jobPostingCreateUseCaseHandler.handle(create);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(FakeJobPostingPort.SUCCESS_CREATION_ID);
    }
}
