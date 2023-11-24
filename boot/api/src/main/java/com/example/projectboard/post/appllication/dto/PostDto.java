package com.example.projectboard.post.appllication.dto;

import com.example.projectboard.member.Member;
import com.example.projectboard.member.application.dto.MemberDto;
import com.example.projectboard.post.Post;

public record PostDto(Long id, MemberDto memberDto, String title, String content, int likeCount) {

    public Post toEntity(Member member) {
        return new Post(member, title, content);
    }

    public static PostDto from(Post post) {
        return new PostDto(post.getId(), MemberDto.from(post.getMember()), post.getTitle(), post.getContent(), post.getLikes().totalLikes());
    }
}
