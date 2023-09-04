package com.example.projectboard.post.appllication;

import com.example.projectboard.member.application.dto.MemberDto;
import com.example.projectboard.member.domain.Member;
import com.example.projectboard.member.domain.MemberRepository;
import com.example.projectboard.post.appllication.dto.PostDto;
import com.example.projectboard.post.domain.Post;
import com.example.projectboard.post.domain.PostRepository;
import com.example.projectboard.support.error.ErrorType;
import com.example.projectboard.support.error.MemberException;
import com.example.projectboard.support.error.PostException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostService(PostRepository postRepository, MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public PostDto findPost(Long id) {
        return postRepository.findById(id).map(PostDto::from)
                .orElseThrow(() -> new PostException(ErrorType.POST_NOT_FOUND, String.format("%s, 게시글이 존재하지 않습니다.", id)));
    }

    @Transactional(readOnly = true)
    public Page<PostDto> findAllPost(Pageable pageable) {
        return postRepository.findAllByDeletedOrderByCreatedAtDesc("N", pageable).map(PostDto::from);
    }

    public PostDto createPost(PostDto postDto) {
        Member member = memberRepository.findByEmail(postDto.memberDto().email())
                .orElseThrow(() -> new MemberException(ErrorType.MEMBER_NOT_FOUND_ERROR, String.format("%s, 회원이 존재하지 않습니다.", postDto.memberDto().email())));
        return PostDto.from(postRepository.save(postDto.toEntity(member)));
    }

    public void modifyPost(Long id, PostDto postDto) {
        Member member = memberRepository.findByEmail(postDto.memberDto().email())
                .orElseThrow(() -> new MemberException(ErrorType.MEMBER_NOT_FOUND_ERROR, String.format("%s, 회원이 존재하지 않습니다.", postDto.memberDto().email())));

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostException(ErrorType.POST_NOT_FOUND, String.format("%s, 게시글이 존재하지 않습니다.", id)));
        post.validationMember(member);
        post.modifyPost(postDto.title(), postDto.content());
    }

    public void deletePost(Long id, MemberDto memberDto) {
        Member member = memberRepository.findByEmail(memberDto.email())
                .orElseThrow(() -> new MemberException(ErrorType.MEMBER_NOT_FOUND_ERROR, String.format("%s, 회원이 존재하지 않습니다.", memberDto.email())));

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostException(ErrorType.POST_NOT_FOUND, String.format("%s, 게시글이 존재하지 않습니다.", id)));
        post.validationMember(member);
        post.deleted();
    }
}
