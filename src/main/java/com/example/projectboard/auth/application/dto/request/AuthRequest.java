package com.example.projectboard.auth.application.dto.request;

import com.example.projectboard.auth.application.dto.AuthDto;
import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
        @NotBlank(message = "이메일은 필수 입력값입니다.")
        String email,

        @NotBlank(message = "비밀번호는 필수 입력값입니다.")
        String password
) {

    public AuthDto toDto() {
        return new AuthDto(email, password);
    }
}
