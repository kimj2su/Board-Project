package com.example.projectboard.post.ui;

import com.example.projectboard.member.application.dto.MemberDto;
import com.example.projectboard.post.appllication.LikeService;
import com.example.projectboard.post.appllication.dto.v1.response.LikeResponse;
import com.example.projectboard.support.response.ApiResponse;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping("/{postId}")
    public ApiResponse<LikeResponse> toggleLike(
            @PathVariable Long postId,
            @AuthenticationPrincipal MemberDto memberDto
    ) {
        return ApiResponse.success(likeService.toggleLike(postId, memberDto));
    }

    @PostMapping("/{postId}")
    public ApiResponse<Void> increaseLike(
            @PathVariable Long postId,
            @AuthenticationPrincipal MemberDto memberDto
    ) {
        likeService.increase(postId, memberDto);
        return ApiResponse.success();
    }

    @DeleteMapping("/{postId}")
    public ApiResponse<Void> decreaseLike(
            @PathVariable Long postId,
            @AuthenticationPrincipal MemberDto memberDto
    ) {
        likeService.decrease(postId, memberDto);
        return ApiResponse.success();
    }
}
