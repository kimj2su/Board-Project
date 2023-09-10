package com.example.projectboard.post.domain;

import com.example.projectboard.member.domain.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Likes {

    @OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    public Likes() {
    }

    public void increase(Member member, Post post) {
        likes.add(new Like(member, post));
    }

    public void decrease(Member member, Post post) {
        likes.stream()
                .filter(like -> like.isSameMember(member))
                .filter(like -> like.isSamePost(post))
                .findFirst()
                .ifPresent(like -> likes.remove(like));
    }

    public int totalLikes() {
        return likes.size();
    }

    public List<Like> getLikes() {
        return likes;
    }
}
