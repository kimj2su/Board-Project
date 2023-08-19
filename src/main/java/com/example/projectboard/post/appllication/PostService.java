package com.example.projectboard.post.appllication;

import com.example.projectboard.post.appllication.dto.PostDto;
import com.example.projectboard.post.domain.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostDto createPost(PostDto postDto) {
        return PostDto.from(postRepository.save(postDto.toEntity()));
    }
}
