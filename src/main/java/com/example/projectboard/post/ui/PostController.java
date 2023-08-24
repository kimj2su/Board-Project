package com.example.projectboard.post.ui;

import com.example.projectboard.member.application.dto.MemberDto;
import com.example.projectboard.post.appllication.PostService;
import com.example.projectboard.post.appllication.dto.v1.request.CreatePostRequestDto;
import com.example.projectboard.post.appllication.dto.v1.request.ModifyPostRequestDto;
import com.example.projectboard.post.appllication.dto.v1.response.PostResponse;
import com.example.projectboard.support.response.ApiResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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


    @GetMapping("/{id}")
    public ApiResponse<PostResponse> getPost(@PathVariable Long id) {
        return ApiResponse.success(PostResponse.from(postService.findPost(id)));
    }

    @PostMapping
    public ApiResponse<PostResponse> createPost(
            @AuthenticationPrincipal MemberDto memberDto,
            @RequestBody CreatePostRequestDto request
    ) {
        return ApiResponse.success(PostResponse.from(postService.createPost(request.toPostDto(memberDto))));
    }

    @PatchMapping("/{id}")
    public ApiResponse<Void> modifyPost(
            @PathVariable Long id,
            @AuthenticationPrincipal MemberDto memberDto,
            @RequestBody ModifyPostRequestDto request
    ) {
        postService.modifyPost(id, request.toPostDto(memberDto));
        return ApiResponse.success();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePost(@PathVariable Long id, @AuthenticationPrincipal MemberDto memberDto) {
        postService.deletePost(id, memberDto);
        return ApiResponse.success();
    }
}
