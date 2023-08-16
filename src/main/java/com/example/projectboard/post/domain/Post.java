package com.example.projectboard.post.domain;

import com.example.projectboard.support.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Where;

@Entity
@Where(clause = "deleted = 'N'")
@DynamicInsert
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    protected Post() {}

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public static PostBuilder builder() {
        return new PostBuilder();
    }

    public static class PostBuilder {

        private String title;
        private String content;

        PostBuilder() {}

        public PostBuilder title(String title) {
            this.title = title;
            return this;
        }

        public PostBuilder content(String content) {
            this.content = content;
            return this;
        }

        public Post build() {
            return new Post(title, content);
        }
    }
}
