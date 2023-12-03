package com.jobf.finder.member.usecase;

import com.jobf.finder.common.model.UseCase;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemberRetrieve implements UseCase {
    Long id;
    String email;
}
