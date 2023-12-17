package com.jobf.finder.job.posting.adapters;

import com.jobf.finder.common.exception.FinderAppDataNotFoundException;
import com.jobf.finder.job.posting.model.JobPosting;
import com.jobf.finder.job.posting.port.JobPostingPort;
import com.jobf.finder.job.posting.usecase.JobPostingCreate;
import com.jobf.finder.job.posting.usecase.JobPostingDelete;
import com.jobf.finder.job.posting.usecase.JobPostingRetrieve;

public class FakeJobPostingPort implements JobPostingPort {

    public static final Long SUCCESS_CREATION_ID = 826L;
    public static final Long NOT_FOUND_POSTING_ID = 444L;
    public static final Long COMMON_OWNER_ID = 999L;

    @Override
    public JobPosting create(JobPostingCreate jobPostingCreate) {
        return JobPosting.builder()
                .id(SUCCESS_CREATION_ID)
                .title(jobPostingCreate.getTitle())
                .description(jobPostingCreate.getDescription())
                .ownerId(jobPostingCreate.getOwnerId())
                .build();
    }

    @Override
    public void delete(JobPostingDelete jobPostingDelete) {}

    @Override
    public JobPosting retrieve(JobPostingRetrieve jobPostingRetrieve) {
        Long requestedJobPostingId = jobPostingRetrieve.getId();
        if (NOT_FOUND_POSTING_ID.compareTo(requestedJobPostingId) == 0) {
            throw new FinderAppDataNotFoundException("jobf.finder.job.posting.notFound");
        };

        return JobPosting.builder()
                .id(requestedJobPostingId)
                .title("title")
                .ownerId(COMMON_OWNER_ID)
                .description("description")
                .build();
    }
}
