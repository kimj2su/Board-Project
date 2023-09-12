package com.example.projectboard.unit.post;

import com.example.projectboard.acceptance.AcceptanceTest;
import com.example.projectboard.member.application.dto.MemberDto;
import com.example.projectboard.member.domain.Member;
import com.example.projectboard.member.domain.MemberRepository;
import com.example.projectboard.member.domain.MemberRole;
import com.example.projectboard.post.appllication.LikeService;
import com.example.projectboard.post.appllication.PostService;
import com.example.projectboard.post.appllication.dto.PostDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

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
        member = memberRepository.save(createMemberDto().toEntity());
        memberDto = MemberDto.from(member);
        postDto = postService.createPost(createPostDto(memberDto));

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

    private MemberDto createMemberDto() {
        return new MemberDto(1L, "김지수", "jisu@email.com", "1234", MemberRole.USER);
    }
}
