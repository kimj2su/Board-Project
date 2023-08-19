package com.example.projectboard.post.appllication.dto.v1.response;

import com.example.projectboard.post.appllication.dto.PostDto;

public record PostResponse() {

    public static PostResponse from(PostDto postDto) {
        return new PostResponse();
    }
}
