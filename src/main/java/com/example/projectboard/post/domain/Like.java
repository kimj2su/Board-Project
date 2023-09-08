package com.example.projectboard.post.domain;

import com.example.projectboard.member.domain.Member;
import com.example.projectboard.support.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicInsert
@DynamicUpdate
public class Like extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    protected Like() {}

    public Like(Member member, Post post) {
        this.member = member;
        this.post = post;
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Post getPost() {
        return post;
    }

    public static LikeBuilder builder() {
        return new LikeBuilder();
    }

    public static class LikeBuilder {
        private Member member;
        private Post post;

        public LikeBuilder() {}

        public LikeBuilder member(Member member) {
            this.member = member;
            return this;
        }

        public LikeBuilder post(Post post) {
            this.post = post;
            return this;
        }

        public Like build() {
            return new Like(member, post);
        }
    }
}
