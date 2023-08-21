package com.example.projectboard.unit.post;

import com.example.projectboard.acceptance.AcceptanceTest;
import com.example.projectboard.member.application.dto.MemberDto;
import com.example.projectboard.member.domain.MemberRole;
import com.example.projectboard.post.appllication.PostService;
import com.example.projectboard.post.appllication.dto.PostDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Post 서비스 테스트")
public class PostServiceTest extends AcceptanceTest {

    @Autowired
    private PostService postService;

    @DisplayName("Post 생성 테스트")
    @Test
    void createPost() {
        // given : 선행조건 기술
        MemberDto memberDto = createMemberDto();
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

    private PostDto createPostDto(MemberDto memberDto) {
        return new PostDto(null, memberDto, "title", "content");
    }

    private MemberDto createMemberDto() {
        return new MemberDto(null, "김지수", "jisu@email.com", "1234", MemberRole.USER);
    }
}
