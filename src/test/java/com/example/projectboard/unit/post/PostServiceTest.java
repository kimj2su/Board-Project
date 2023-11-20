package com.example.projectboard.unit.post;

import com.example.projectboard.acceptance.AcceptanceTest;
import com.example.projectboard.member.application.dto.MemberDto;
import com.example.projectboard.member.domain.Member;
import com.example.projectboard.member.domain.MemberRepository;
import com.example.projectboard.member.domain.MemberRole;
import com.example.projectboard.post.appllication.PostService;
import com.example.projectboard.post.appllication.dto.PostDto;
import com.example.projectboard.support.error.PostException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Post 서비스 테스트")
public class PostServiceTest extends AcceptanceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private MemberRepository memberRepository;

    private MemberDto memberDto;

    @BeforeEach
    void before() {
        String existingEmail = "jisu@email.com";
        Member member = memberRepository.save(createMemberDto(existingEmail).toEntity());
        memberDto = MemberDto.from(member);
    }

    @DisplayName("Post 생성 테스트")
    @Test
    void createPost() {
        // given : 선행조건 기술
        PostDto request = createPostDto(memberDto);

        // when : 기능 수행
        PostDto postdto = postService.createPost(request);

        // then : 결과 확인
        assertThat(postdto.id()).isEqualTo(1L);
        assertThat(postdto.title()).isEqualTo("title");
        assertThat(postdto.content()).isEqualTo("content");
        assertThat(postdto.memberDto().name()).isEqualTo("김지수");
        assertThat(postdto.memberDto().email()).isEqualTo("jisu@email.com");
    }

    @DisplayName("Post 수정 테스트")
    @Test
    void modifyPost() {
        // given : 선행조건 기술
        PostDto request = createPostDto(memberDto);
        PostDto postdto = postService.createPost(request);
        Long id = postdto.id();

        // when : 기능 수행
        postService.modifyPost(id, new PostDto(null, memberDto, "title2", "content2", 0));

        // then : 결과 확인
        PostDto findPost = postService.findPost(id);
        assertThat(findPost.id()).isEqualTo(1L);
        assertThat(findPost.title()).isEqualTo("title2");
        assertThat(findPost.content()).isEqualTo("content2");
    }

    @DisplayName("Post 조회 테스트")
    @Test
    void findPost() {
        // given : 선행조건 기술
        PostDto request = createPostDto(memberDto);
        PostDto postdto = postService.createPost(request);
        Long id = postdto.id();

        // when : 기능 수행
        PostDto findPost = postService.findPost(id);

        // then : 결과 확인
        assertThat(findPost.id()).isEqualTo(1L);
        assertThat(findPost.title()).isEqualTo("title");
        assertThat(findPost.content()).isEqualTo("content");
    }

    @DisplayName("Post 조회 페이징 테스트")
    @Test
    void findAllPost() {
        // given : 선행조건 기술
        PostDto request = createPostDto(memberDto);
        PostDto postdto = postService.createPost(request);
        Long id = postdto.id();
        Pageable pageable = Pageable.ofSize(20);

        // when : 기능 수행
        Page<PostDto> posts = postService.findAllPost(pageable);

        // then : 결과 확인
        assertThat(posts.getTotalElements()).isEqualTo(1L);
        assertThat(posts).hasSize(1)
                .extracting("id", "title", "content")
                .contains(tuple(id, postdto.title(), postdto.content()));
    }

    @DisplayName("Post 삭제 테스트")
    @Test
    void deletePost() {
        // given : 선행조건 기술
        PostDto request = createPostDto(memberDto);
        PostDto postdto = postService.createPost(request);
        Long id = postdto.id();

        // when : 기능 수행
        postService.deletePost(id, memberDto);

        // then : 결과 확인
        assertThatThrownBy(() -> {
            postService.findPost(id);
        }).isInstanceOf(PostException.class)
                .hasMessageContaining("게시글이 존재하지 않습니다.");
    }

    @DisplayName("Post 본인 확인 테스트")
    @Test
    void validationPost() {
        // given : 선행조건 기술
        PostDto request = createPostDto(memberDto);
        PostDto postdto = postService.createPost(request);
        Long id = postdto.id();

        // when : 기능 수행
        postService.validationPost(id, memberDto);

        // then : 결과 확인
        PostDto findPost = postService.findPost(id);
        assertThat(findPost.id()).isEqualTo(1L);
        assertThat(findPost.title()).isEqualTo("title");
        assertThat(findPost.content()).isEqualTo("content");
        assertThat(findPost.memberDto().id()).isEqualTo(1L);
    }

    @DisplayName("Post 본인 확인 테스트 - 실패 게시글이 없을 때")
    @Test
    void validationPostReturnThrowsPostException() {
        // given : 선행조건 기술
        PostDto request = createPostDto(memberDto);
        PostDto postdto = postService.createPost(request);
        Long id = postdto.id();
        postService.deletePost(id, memberDto);

        // when : 기능 수행 & then : 결과 확인
        Assertions.assertThatThrownBy(() -> postService.validationPost(id, memberDto))
                .isInstanceOf(PostException.class)
                .hasMessageContaining("1, 게시글이 존재하지 않습니다.");
    }

    private PostDto createPostDto(MemberDto memberDto) {
        return new PostDto(null, memberDto, "title", "content", 0);
    }

    private MemberDto createMemberDto(String email) {
        return new MemberDto(null, "김지수", email, "1234", MemberRole.USER);
    }
}
