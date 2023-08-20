package com.example.projectboard.auth.application.dto.request;

import com.example.projectboard.auth.application.dto.AuthDto;

public record AuthRequest(String email, String password) {

    public AuthDto toDto() {
        return new AuthDto(email, password);
    }
}
