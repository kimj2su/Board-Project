package com.example.projectboard.post;


import com.example.projectboard.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.example.projectboard.support.error.PostException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@DisplayName("Post 도메인 테스트")
class PostTest {

    @DisplayName("Post 생성 테스트")
    @Test
    void createPost() {
        Member member = createMember(1L);
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
        Member member = createMember(1L);
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

    @DisplayName("Post 수정자 테스트")
    @Test
    void validationMember() {
        Member member = createMember(1L);
        Member member2 = createMember(2L);
        Post post = Post.builder()
                .member(member)
                .title("title")
                .content("content")
                .build();

        assertThatThrownBy(() -> post.validationMember(member2))
                .isInstanceOf(PostException.class)
                .hasMessage("작성자가 아닙니다.");
    }

    private Member createMember(Long id) {
        return Member.of(id, "name", "email", "password");
    }
}