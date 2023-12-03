package com.jobf.finder.adapters.member.rest.dto;

import com.jobf.finder.member.model.Authentication;
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
public class MemberLoginResponse {

    String tokenType;
    String accessToken;
    String refreshToken;

    public static MemberLoginResponse from(Authentication authentication) {
        return MemberLoginResponse.builder().tokenType(authentication.getTokenType()).accessToken(authentication.getToken()).refreshToken(authentication.getRefreshToken()).build();
    }
}
