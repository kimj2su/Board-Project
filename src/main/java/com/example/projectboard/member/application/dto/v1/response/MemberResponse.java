package com.example.projectboard.member.application.dto.v1.response;


import com.example.projectboard.member.application.dto.MemberDto;

public record MemberResponse(
    Long id,
    String name,
    String email
) {

    public static MemberResponse from(MemberDto memberDto) {
        return new MemberResponse(
            memberDto.id(),
            memberDto.name(),
            memberDto.email()
        );
    }
}
