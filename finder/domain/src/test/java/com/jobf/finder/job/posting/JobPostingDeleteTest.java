package com.jobf.finder.job.posting;

import com.jobf.finder.common.exception.FinderAppDataNotFoundException;
import com.jobf.finder.job.posting.adapters.FakeJobPostingPort;
import com.jobf.finder.job.posting.usecase.JobPostingDelete;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class JobPostingDeleteTest {

    JobPostingDeleteUseCaseHandler jobPostingDeleteUseCaseHandler;

    @BeforeEach
    void setUp() {
        jobPostingDeleteUseCaseHandler = new JobPostingDeleteUseCaseHandler(new FakeJobPostingPort());
    }

    @Test
    void shouldDelete() {
        // given
        var delete = JobPostingDelete.builder()
                .id(334L)
                .requesterId(FakeJobPostingPort.COMMON_OWNER_ID)
                .build();

        // when
        jobPostingDeleteUseCaseHandler.handle(delete);

        // then - no data
    }

    @Test
    void shouldNotDeleteBecauseOwnerAndRequesterAreNotTheSame() {
        // given
        var delete = JobPostingDelete.builder()
                .id(333L)
                .requesterId(FakeJobPostingPort.COMMON_OWNER_ID - 1L)
                .build();

        assertThatExceptionOfType(FinderAppDataNotFoundException.class)
                .isThrownBy(() -> jobPostingDeleteUseCaseHandler.handle(delete))
                .withMessage("jobf.finder.unAuthorizedAction");
    }
}
