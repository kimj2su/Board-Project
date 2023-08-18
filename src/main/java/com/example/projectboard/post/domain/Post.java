package com.example.projectboard.post.domain;

import com.example.projectboard.member.domain.Member;
import com.example.projectboard.support.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Where;

@Entity
@Where(clause = "deleted = 'N'")
@DynamicInsert
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    @Lob
    private String content;

    protected Post() {}

    public Post(Member member, String title, String content) {
        this.member = member;
        this.title = title;
        this.content = content;
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

    public void modifyPost(String title, String content) {
        this.title = title;
        this.content = content;
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
