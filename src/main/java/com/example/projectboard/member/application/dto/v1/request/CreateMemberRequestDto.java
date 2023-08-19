package com.example.projectboard.member.application.dto.v1.request;

import com.example.projectboard.member.application.dto.MemberDto;

public record CreateMemberRequestDto(String name, String email, String password) {

    public MemberDto toMemberDto() {
        return new MemberDto(null, name, email, password, null);
    }
}
