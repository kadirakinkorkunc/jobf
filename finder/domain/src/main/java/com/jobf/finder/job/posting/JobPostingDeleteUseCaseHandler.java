package com.jobf.finder.job.posting;

import com.jobf.finder.common.DomainComponent;
import com.jobf.finder.common.exception.FinderAppDataNotFoundException;
import com.jobf.finder.common.usecase.VoidUseCaseHandler;
import com.jobf.finder.job.posting.model.JobPosting;
import com.jobf.finder.job.posting.port.JobPostingPort;
import com.jobf.finder.job.posting.usecase.JobPostingDelete;
import com.jobf.finder.job.posting.usecase.JobPostingRetrieve;
import lombok.RequiredArgsConstructor;

@DomainComponent
@RequiredArgsConstructor
public class JobPostingDeleteUseCaseHandler implements VoidUseCaseHandler<JobPostingDelete> {

    private final JobPostingPort jobPostingPort;

    @Override
    public void handle(JobPostingDelete jobPostingDelete) {
        validate(jobPostingDelete);
        jobPostingPort.delete(jobPostingDelete);
    }

    private void validate(JobPostingDelete jobPostingDelete) {
        JobPosting jobPosting = jobPostingPort.retrieve(JobPostingRetrieve.builder().id(jobPostingDelete.getId()).build());

        /** fixme:authorization
        * For now only the creator of the job posting can delete the job posting.
        * After authorization impl. admins should be able to delete the job postings too.
        **/
        if (jobPostingDelete.getRequesterId().compareTo(jobPosting.getOwnerId()) != 0) {
            throw new FinderAppDataNotFoundException("jobf.finder.unAuthorizedAction");
        }
    }
}
