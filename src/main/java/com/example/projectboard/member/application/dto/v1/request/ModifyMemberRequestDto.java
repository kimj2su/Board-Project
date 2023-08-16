package com.example.projectboard.member.application.dto.v1.request;

import com.example.projectboard.member.application.dto.MemberDto;

public record ModifyMemberRequestDto(String name, String email) {
    public MemberDto toMemberDto() {
        return new MemberDto(null, name, email, null);
    }
}
