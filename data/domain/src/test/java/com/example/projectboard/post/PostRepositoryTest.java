package com.example.projectboard.post;

import com.example.projectboard.member.Member;
import com.example.projectboard.member.MemberRepository;
import com.example.projectboard.utils.DomainContextTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("PostRepository 테스트")
public class PostRepositoryTest extends DomainContextTest {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private Member member;

    public PostRepositoryTest(PostRepository postRepository, MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    @BeforeEach
    void createMember() {
        member = memberRepository.save(Member.builder()
                .name("김지수")
                .email("kimjisu3268@gmail.com")
                .build());
    }

    @DisplayName("게시글 저장")
    @Test
    void save() {
        // given
        Post post = createPost();

        // when
        Post savedPost = postRepository.save(post);

        // then
        assertThat(savedPost.getId()).isNotNull();
        assertThat(savedPost.getTitle()).isEqualTo(post.getTitle());
        assertThat(savedPost.getContent()).isEqualTo(post.getContent());
    }

    @DisplayName("게시글 조회")
    @Test
    void find() {
        // given
        Post post = createPost();
        Post savedPost = postRepository.save(post);

        // when
        Post findPost = postRepository.findById(1L).get();

        // then
        assertThat(findPost.getId()).isNotNull();
        assertThat(findPost.getTitle()).isEqualTo(savedPost.getTitle());
        assertThat(findPost.getContent()).isEqualTo(savedPost.getContent());
    }

    @DisplayName("게시글 수정")
    @Test
    @Transactional
    void modify() {
        // given
        Post post = createPost();
        Post savedPost = postRepository.save(post);
        String modifyTitle = "수정된 제목";
        String modifyContent = "수정된 내용";

        // when
        post.modifyPost(modifyTitle, modifyContent);

        // then
        Post findPost = postRepository.findById(1L).get();
        assertThat(savedPost.getId()).isNotNull();
        assertThat(findPost.getTitle()).isEqualTo(modifyTitle);
        assertThat(findPost.getContent()).isEqualTo(modifyContent);
    }

    @DisplayName("게시글 삭제")
    @Test
    @Transactional
    void delete() {
        // given
        Post post = createPost();
        Post savedPost = postRepository.save(post);

        // when
        postRepository.delete(savedPost);

        // then
        Optional<Post> findPost = postRepository.findById(1L);
        assertThat(findPost).isEmpty();
    }


    private Post createPost() {
        return Post.builder()
                .member(member)
                .title("제목")
                .content("내용")
                .build();
    }
}
