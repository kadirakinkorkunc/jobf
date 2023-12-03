package com.jobf.finder.member.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Authentication {

    String tokenType;
    String token;
    String refreshToken;
}
