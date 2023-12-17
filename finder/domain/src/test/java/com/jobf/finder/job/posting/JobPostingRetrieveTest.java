package com.jobf.finder.job.posting;

import com.jobf.finder.common.exception.FinderAppDataNotFoundException;
import com.jobf.finder.job.posting.adapters.FakeJobPostingPort;
import com.jobf.finder.job.posting.usecase.JobPostingRetrieve;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class JobPostingRetrieveTest {

    JobPostingRetrieveUseCaseHandler jobPostingRetrieveUseCaseHandler;

    @BeforeEach
    void setUp() {
        jobPostingRetrieveUseCaseHandler = new JobPostingRetrieveUseCaseHandler(new FakeJobPostingPort());
    }

    @Test
    void shouldRetrieve() {
        // given
        var retrieve = JobPostingRetrieve.builder().id(11111L).build();

        // when
        var response = jobPostingRetrieveUseCaseHandler.handle(retrieve);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getTitle()).isEqualTo("title");
    }

    @Test
    void shouldNotRetrieveBecauseNotFound() {
        // given
        var retrieve = JobPostingRetrieve.builder().id(FakeJobPostingPort.NOT_FOUND_POSTING_ID).build();

        // when
        assertThatExceptionOfType(FinderAppDataNotFoundException.class)
                .isThrownBy(() -> jobPostingRetrieveUseCaseHandler.handle(retrieve))
                .withMessage("jobf.finder.job.posting.notFound");
    }
}
