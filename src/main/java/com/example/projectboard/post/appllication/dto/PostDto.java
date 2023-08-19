package com.example.projectboard.post.appllication.dto;

import com.example.projectboard.member.application.dto.MemberDto;
import com.example.projectboard.post.domain.Post;

public record PostDto(MemberDto memberDto, String title, String content) {

    public Post toEntity() {
        return new Post(memberDto.toEntity(), title, content);
    }

    public static PostDto from(Post post) {
        return new PostDto(MemberDto.from(post.getMember()), post.getTitle(), post.getContent());
    }
}
