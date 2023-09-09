package com.example.projectboard.post.appllication;

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

    public void increase(Long postId) {
        // postRepository.findById(postId)
        //         .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."))
        //         .increaseLikeCount();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorType.POST_NOT_FOUND, String.format("%s, 게시글이 존재하지 않습니다.", postId)));

    }
}
