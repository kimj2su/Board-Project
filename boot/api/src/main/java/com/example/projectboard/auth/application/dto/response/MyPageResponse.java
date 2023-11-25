package com.example.projectboard.auth.application.dto.response;


import com.example.projectboard.member.Level;
import com.example.projectboard.member.application.dto.MemberDto;

public record MyPageResponse(
    Long id,
    String name,
    String email,
    Level level
) {

    public static MyPageResponse from(MemberDto memberDto) {
        return new MyPageResponse(
                memberDto.id(),
                memberDto.name(),
                memberDto.email(),
                memberDto.level()
        );
    }
}
