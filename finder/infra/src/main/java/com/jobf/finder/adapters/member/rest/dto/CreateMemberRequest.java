package com.jobf.finder.adapters.member.rest.dto;

import com.jobf.finder.common.rest.dto.AbstractRequest;
import com.jobf.finder.member.usecase.MemberCreate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateMemberRequest extends AbstractRequest {

    @NotBlank
    @Email
    String email;

    @NotBlank
    String password;

    public MemberCreate toUseCase() {
        return MemberCreate.builder().email(email).password(password).build();
    }
}
