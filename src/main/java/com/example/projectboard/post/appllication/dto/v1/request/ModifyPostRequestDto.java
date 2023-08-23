package com.example.projectboard.post.appllication.dto.v1.request;

import com.example.projectboard.member.application.dto.MemberDto;
import com.example.projectboard.post.appllication.dto.PostDto;

public record ModifyPostRequestDto(String title, String content) {

    public PostDto toPostDto(MemberDto memberDto) {
        return new PostDto(null, memberDto, title, content);
    }
}
