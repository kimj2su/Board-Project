package com.example.projectboard.post.appllication.dto.v1.response;

public record LikeResponse(boolean isLike) {

        public static LikeResponse from(boolean isLike) {
            return new LikeResponse(isLike);
        }
}
