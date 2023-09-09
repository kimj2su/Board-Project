package com.example.projectboard.post.ui;

import com.example.projectboard.post.appllication.LikeService;
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

    @PostMapping
    public void like(Long postId) {
        likeService.increase(postId);
    }
}
