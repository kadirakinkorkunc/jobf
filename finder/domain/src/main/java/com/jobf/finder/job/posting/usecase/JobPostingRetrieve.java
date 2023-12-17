package com.jobf.finder.job.posting.usecase;

import com.jobf.finder.common.model.UseCase;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;


@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobPostingRetrieve implements UseCase {
    Long id;
}
