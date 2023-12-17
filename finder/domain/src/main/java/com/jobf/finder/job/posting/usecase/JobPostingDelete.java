package com.jobf.finder.job.posting.usecase;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobPostingDelete {
    Long id;
    Long requesterId;
}
