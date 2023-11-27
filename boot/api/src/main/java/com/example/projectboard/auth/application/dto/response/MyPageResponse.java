package com.example.projectboard.auth.application.dto.response;


import com.example.projectboard.member.MemberLevel;
import com.example.projectboard.member.application.dto.MemberDto;

public record MyPageResponse(
    Long id,
    String name,
    String email,
    MemberLevel memberLevel
) {

    public static MyPageResponse from(MemberDto memberDto) {
        return new MyPageResponse(
                memberDto.id(),
                memberDto.name(),
                memberDto.email(),
                memberDto.memberLevel()
        );
    }
}
