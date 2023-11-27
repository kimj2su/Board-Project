package com.example.projectboard.unit.post;

import com.example.projectboard.acceptance.AcceptanceTest;
import com.example.projectboard.member.MemberLevel;
import com.example.projectboard.member.Member;
import com.example.projectboard.member.MemberRepository;
import com.example.projectboard.member.MemberRole;
import com.example.projectboard.member.application.dto.MemberDto;
import com.example.projectboard.post.appllication.LikeService;
import com.example.projectboard.post.appllication.PostService;
import com.example.projectboard.post.appllication.dto.PostDto;
import com.example.projectboard.post.appllication.dto.v1.response.LikeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.projectboard.support.error.PostException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("좋아요 서비스 테스트")
public class LikeServiceTest extends AcceptanceTest {

    @Autowired
    private LikeService likeService;

    @Autowired
    private PostService postService;

    @Autowired
    private MemberRepository memberRepository;


    private MemberDto memberDto;
    private PostDto postDto;
    private Member member;
    @BeforeEach
    void before() {
        member = memberRepository.save(createMemberDto("jisu@email.com").toEntity());
        memberDto = MemberDto.from(member);
        postDto = postService.createPost(createPostDto(memberDto));
    }

    @DisplayName("좋아요 증가시 게시글이 존재하지 않으면 예외 발생")
    @Test
    void increaseLikeThenReturnThrows() {
        // given : 선행조건 기술
        Long postId = postDto.id();
        postService.deletePost(postId, memberDto);

        // when : 기능 수행 & then : 결과 확인
        assertThatThrownBy(() -> likeService.increase(postId, memberDto))
                .isInstanceOf(PostException.class)
                .hasMessage("1, 게시글이 존재하지 않습니다.");
    }

    @DisplayName("좋아요 확인 - 좋아요 하지 않은 경우")
    @Test
    void checkLikeIsFalse() {
        // given : 선행조건 기술
        Long postId = postDto.id();

        // when : 기능 수행
        LikeResponse likeResponse = likeService.toggleLike(postId, memberDto);

        // then : 결과 확인
        assertThat(likeResponse.isLike()).isFalse();
    }

    @DisplayName("좋아요 확인 - 좋아요 한 경우")
    @Test
    void checkLikeIsTrue() {
        // given : 선행조건 기술
        Long postId = postDto.id();
        likeService.increase(postId, memberDto);

        // when : 기능 수행
        LikeResponse likeResponse = likeService.toggleLike(postId, memberDto);

        // then : 결과 확인
        assertThat(likeResponse.isLike()).isTrue();
    }

    @DisplayName("좋아요 증가 테스트")
    @Test
    void increaseLike() {
        // given : 선행조건 기술
        Long postId = postDto.id();

        // when : 기능 수행
        likeService.increase(postId, memberDto);

        // then : 결과 확인
        PostDto post = postService.findPost(postId);
        assertThat(post.likeCount()).isEqualTo(1);
    }

    @DisplayName("좋아요 감소 테스트")
    @Test
    void decreaseLike() {
        // given : 선행조건 기술
        Long postId = postDto.id();
        likeService.increase(postId, memberDto);

        // when : 기능 수행
        likeService.decrease(postId, memberDto);

        // then : 결과 확인
        PostDto post = postService.findPost(postId);
        assertThat(post.likeCount()).isEqualTo(0);
    }

    private PostDto createPostDto(MemberDto memberDto) {
        return new PostDto(null, memberDto, "title", "content", 0);
    }

    private MemberDto createMemberDto(String email) {
        return new MemberDto( null, "김지수", email, "1234", MemberRole.USER, MemberLevel.NORMAL);
    }
}
