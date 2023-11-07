package com.example.projectboard.post.domain;

import com.example.projectboard.member.domain.Member;
import com.example.projectboard.support.entity.BaseEntity;
import com.example.projectboard.support.error.ErrorType;
import com.example.projectboard.support.error.PostException;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Objects;

@Entity
@Where(clause = "deleted = 'N'")
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    @Lob
    private String content;

    @Embedded
    private Likes likes = new Likes();

    protected Post() {}

    public Post(Member member, String title, String content) {
        this.member = member;
        this.title = title;
        this.content = content;
    }

    public void modifyPost(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void validationMember(Member member) {
        if (this.member != member) {
            throw new PostException(ErrorType.MEMBER_PERMISSION_ERROR, "작성자가 아닙니다.");
        }
    }

    public void increaseLike(Member member) {
        likes.increase(member, this);
    }

    public void decreaseLike(Member member) {
        likes.decrease(member, this);
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Likes getLikes() {
        return likes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(getId(), post.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public static PostBuilder builder() {
        return new PostBuilder();
    }

    public static class PostBuilder {

        private Member member;
        private String title;
        private String content;

        PostBuilder() {}

        public PostBuilder member(Member member) {
            this.member = member;
            return this;
        }

        public PostBuilder title(String title) {
            this.title = title;
            return this;
        }

        public PostBuilder content(String content) {
            this.content = content;
            return this;
        }

        public Post build() {
            return new Post(member, title, content);
        }
    }
}
