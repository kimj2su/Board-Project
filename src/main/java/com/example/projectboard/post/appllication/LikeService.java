package com.example.projectboard.post.appllication;

import com.example.projectboard.member.application.dto.MemberDto;
import com.example.projectboard.member.domain.Member;
import com.example.projectboard.post.appllication.dto.v1.response.LikeResponse;
import com.example.projectboard.post.domain.Post;
import com.example.projectboard.post.domain.PostRepository;
import com.example.projectboard.support.error.ErrorType;
import com.example.projectboard.support.error.PostException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LikeService {

    private final PostRepository postRepository;

    public LikeService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public LikeResponse toggleLike(Long postId, MemberDto memberDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorType.POST_NOT_FOUND, String.format("%s, 게시글이 존재하지 않습니다.", postId)));
        Member member = memberDto.toEntity();

        return LikeResponse.from(post.getLikes().getLikes()
                .stream()
                .anyMatch(like -> like.getMember().equals(member)));
    }

    public void increase(Long postId, MemberDto memberDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorType.POST_NOT_FOUND, String.format("%s, 게시글이 존재하지 않습니다.", postId)));
        Member member = memberDto.toEntity();

        post.increaseLike(member);
    }

    public void decrease(Long postId, MemberDto memberDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorType.POST_NOT_FOUND, String.format("%s, 게시글이 존재하지 않습니다.", postId)));
        Member member = memberDto.toEntity();

        post.decreaseLike(member);
    }
}
