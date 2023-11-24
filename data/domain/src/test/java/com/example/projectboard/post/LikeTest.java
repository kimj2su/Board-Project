package com.example.projectboard.post;

import com.example.projectboard.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("좋아요 도메인 테스트")
public class LikeTest {

    @DisplayName("좋아요 도메인 생성 테스트")
    @Test
    void createLike() {
        // given : 선행조건 기술
        Member member = createMember();
        Post post = createPost(member);

        // when : 기능 수행
        Like like = createLike(post, member);

        // then : 결과 확인
        assertThat(like.getId()).isEqualTo(1L);
        assertThat(like.getPost()).isEqualTo(post);
        assertThat(like.getMember()).isEqualTo(member);
    }

    private Like createLike(Post post, Member member) {
        Like like = Like.builder()
                .post(post)
                .member(member)
                .build();
        ReflectionTestUtils.setField(like, "id", 1L);

        return like;
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
