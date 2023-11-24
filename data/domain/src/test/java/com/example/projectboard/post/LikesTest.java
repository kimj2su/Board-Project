package com.example.projectboard.post;

import com.example.projectboard.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("좋아요 생성 주기 테스트")
public class LikesTest {

    @DisplayName("좋아요 추가 테스트")
    @Test
    void increaseLike() {
        // given : 선행조건 기술
        Member member = createMember();
        Post post = createPost(member);
        Likes likes = new Likes();

        // when : 기능 수행
        likes.increase(member, post);

        // then : 결과 확인
        assertThat(likes.totalLikes()).isEqualTo(1);
        assertThat(likes.getLikes()).hasSize(1)
                .extracting("member", "post")
                .containsExactly(tuple(member, post));
    }

    @DisplayName("좋아요 삭제 테스트")
    @Test
    void decreaseLike() {
        // given : 선행조건 기술
        Member member = createMember();
        Post post = createPost(member);
        Likes likes = new Likes();
        likes.increase(member, post);

        // when : 기능 수행
        likes.decrease(member, post);

        // then : 결과 확인
        assertThat(likes.totalLikes()).isEqualTo(0);
    }

    private Post createPost(Member member) {
        return Post.builder()
                .member(member)
                .title("title")
                .content("content")
                .build();
    }

    private Member createMember() {
        return Member.builder()
                .email("email@email.com")
                .name("name")
                .password("password")
                .build();
    }
}
