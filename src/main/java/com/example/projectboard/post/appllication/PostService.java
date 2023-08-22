package com.example.projectboard.post.appllication;

import com.example.projectboard.post.appllication.dto.PostDto;
import com.example.projectboard.post.domain.Post;
import com.example.projectboard.post.domain.PostRepository;
import com.example.projectboard.support.error.ErrorType;
import com.example.projectboard.support.error.PostException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional(readOnly = true)
    public PostDto findPost(Long id) {
        return postRepository.findById(id).map(PostDto::from)
                .orElseThrow(() -> new PostException(ErrorType.POST_NOT_FOUND, String.format("%s, 게시글이 존재하지 않습니다.", id)));
    }

    public PostDto createPost(PostDto postDto) {
        return PostDto.from(postRepository.save(postDto.toEntity()));
    }

    public void modifyPost(Long id, PostDto postDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostException(ErrorType.POST_NOT_FOUND, String.format("%s, 게시글이 존재하지 않습니다.", id)));
        post.modifyPost(postDto.title(), postDto.content());
    }

    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostException(ErrorType.POST_NOT_FOUND, String.format("%s, 게시글이 존재하지 않습니다.", id)));
        post.deleted();
    }
}
