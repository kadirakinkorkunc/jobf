package com.jobf.finder.job.posting.port;

import com.jobf.finder.job.posting.model.JobPosting;
import com.jobf.finder.job.posting.usecase.JobPostingCreate;
import com.jobf.finder.job.posting.usecase.JobPostingDelete;
import com.jobf.finder.job.posting.usecase.JobPostingRetrieve;

public interface JobPostingPort {

    JobPosting create(JobPostingCreate jobPostingCreate);

    void delete(JobPostingDelete jobPostingDelete);

    JobPosting retrieve(JobPostingRetrieve id);
}
