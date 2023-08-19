package com.example.projectboard.post.ui;

import com.example.projectboard.member.application.dto.MemberDto;
import com.example.projectboard.post.appllication.PostService;
import com.example.projectboard.post.appllication.dto.v1.request.CreatePostRequestDto;
import com.example.projectboard.post.appllication.dto.v1.response.PostResponse;
import com.example.projectboard.support.response.ApiResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ApiResponse<PostResponse> createPost(@AuthenticationPrincipal MemberDto memberDto, @RequestBody CreatePostRequestDto request) {
        return ApiResponse.success(PostResponse.from(postService.createPost(request.toPostDto(memberDto))));
    }
}
