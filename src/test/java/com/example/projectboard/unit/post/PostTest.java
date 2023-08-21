package com.example.projectboard.unit.post;


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

    @DisplayName("Post 수정 테스트")
    @Test
    void modifyPost() {
        Member member = createMember();
        Post post = Post.builder()
                .member(member)
                .title("title")
                .content("content")
                .build();

        post.modifyPost("title2", "content2");

        assertThat(post.getMember()).isEqualTo(member);
        assertThat(post.getTitle()).isEqualTo("title2");
        assertThat(post.getContent()).isEqualTo("content2");
    }

    private Member createMember() {
        return Member.builder()
                .email("email@email.com")
                .name("name")
                .password("password")
                .build();
    }
}