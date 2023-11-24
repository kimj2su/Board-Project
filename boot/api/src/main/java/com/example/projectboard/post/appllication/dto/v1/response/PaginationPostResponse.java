package com.example.projectboard.post.appllication.dto.v1.response;

import java.util.List;

public record PaginationPostResponse (
        int currentPageNumber,
        int totalPages,
        List<PostResponse> posts,
        List<Integer> paginationBarNumbers
) {

    public static PaginationPostResponse from(int currentPageNumber, int totalPages, List<PostResponse> posts, List<Integer> paginationBarNumbers) {
        return new PaginationPostResponse(currentPageNumber, totalPages, posts, paginationBarNumbers);
    }
}
