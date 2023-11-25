package com.example.projectboard.member.application.dto.v1.request;

import com.example.projectboard.member.application.dto.MemberDto;
import jakarta.validation.constraints.NotBlank;

public record CreateMemberRequestDto(

        @NotBlank(message = "이름은 필수 입력값입니다.")
        String name,
        @NotBlank(message = "이메일은 필수 입력값입니다.")
        String email,
        @NotBlank(message = "비밀번호는 필수 입력값입니다.")
        String password
) {

    public MemberDto toMemberDto() {
        return new MemberDto(null, name, email, password, null, null);
    }
}
