package com.example.projectboard.unit.post.domain;


import com.example.projectboard.member.domain.Member;
import com.example.projectboard.post.domain.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Post 도메인 테스트")
class PostTest {

    @DisplayName("Post 생성 테스트")
    @Test
    void createPost() {
        Member member = createMember();
        Post post = Post.builder()
                .member(member)
                .title("title")
                .content("content")
                .build();

        assertThat(post.getMember()).isEqualTo(member);
        assertThat(post.getTitle()).isEqualTo("title");
        assertThat(post.getContent()).isEqualTo("content");
    }

    private Member createMember() {
        return Member.builder()
                .email("email@email.com")
                .name("name")
                .password("password")
                .build();
    }
}