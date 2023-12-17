package com.jobf.finder.job.posting;

import com.jobf.finder.common.DomainComponent;
import com.jobf.finder.common.usecase.UseCaseHandler;
import com.jobf.finder.job.posting.model.JobPosting;
import com.jobf.finder.job.posting.port.JobPostingPort;
import com.jobf.finder.job.posting.usecase.JobPostingCreate;
import lombok.RequiredArgsConstructor;

@DomainComponent
@RequiredArgsConstructor
public class JobPostingCreateUseCaseHandler implements UseCaseHandler<JobPosting, JobPostingCreate> {

    private final JobPostingPort jobPostingPort;

    @Override
    public JobPosting handle(JobPostingCreate useCase) {
        return jobPostingPort.create(useCase);
    }
}
