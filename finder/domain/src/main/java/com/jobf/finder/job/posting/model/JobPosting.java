package com.jobf.finder.job.posting.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobPosting {
    Long id;
    Long ownerId;
    String title;
    String description;
}
