package com.jobf.finder.job.posting;

import com.jobf.finder.common.DomainComponent;
import com.jobf.finder.common.usecase.UseCaseHandler;
import com.jobf.finder.job.posting.model.JobPosting;
import com.jobf.finder.job.posting.port.JobPostingPort;
import com.jobf.finder.job.posting.usecase.JobPostingRetrieve;
import lombok.RequiredArgsConstructor;

@DomainComponent
@RequiredArgsConstructor
public class JobPostingRetrieveUseCaseHandler implements UseCaseHandler<JobPosting, JobPostingRetrieve> {

    private final JobPostingPort jobPostingPort;

    @Override
    public JobPosting handle(JobPostingRetrieve useCase) {
        return jobPostingPort.retrieve(useCase);
    }
}
