package com.jobf.finder.adapters.member.rest.dto;

import com.jobf.finder.member.model.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemberResponse {
    Long id;
    String email;

    public static MemberResponse from(Member member) {
        return MemberResponse.builder().id(member.getId()).email(member.getEmail()).build();
    }
}
