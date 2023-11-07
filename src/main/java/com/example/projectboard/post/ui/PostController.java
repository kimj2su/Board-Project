package com.example.projectboard.post.ui;

import com.example.projectboard.member.application.dto.MemberDto;
import com.example.projectboard.post.appllication.PostService;
import com.example.projectboard.post.appllication.dto.PostDto;
import com.example.projectboard.post.appllication.dto.v1.request.CreatePostRequestDto;
import com.example.projectboard.post.appllication.dto.v1.request.ModifyPostRequestDto;
import com.example.projectboard.post.appllication.dto.v1.response.PaginationPostResponse;
import com.example.projectboard.post.appllication.dto.v1.response.PostResponse;
import com.example.projectboard.support.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final PaginationService paginationService;

    public PostController(PostService postService, PaginationService paginationService) {
        this.postService = postService;
        this.paginationService = paginationService;
    }


    @GetMapping("/{id}")
    public ApiResponse<PostResponse> findPost(@PathVariable Long id) {
        return ApiResponse.success(PostResponse.from(postService.findPost(id)));
    }

    @GetMapping
    public ApiResponse<PaginationPostResponse> findAllPost(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<PostDto> posts = postService.findAllPost(pageable);
        List<Integer> paginationBarNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), posts.getTotalPages());

        return ApiResponse.success(PaginationPostResponse.from(posts.getNumber(), posts.getTotalPages(), posts.map(PostResponse::from).stream().toList(), paginationBarNumbers));
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

    @PostMapping("/{id}/validation")
    public ApiResponse<Void> validationPost(
            @PathVariable Long id,
            @AuthenticationPrincipal MemberDto memberDto
    ) {
        postService.validationPost(id, memberDto);
        return ApiResponse.success();
    }
}
