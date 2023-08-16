package com.example.projectboard.member.application.dto;


import com.example.projectboard.member.domain.Member;

public record MemberDto(Long id, String name, String email, String password) {

    public Member toEntity() {
        return new Member(name, email, password);
    }

    public static MemberDto from(Member member) {
        return new MemberDto(member.getId(), member.getName(), member.getEmail(), member.getPassword());
    }
}
