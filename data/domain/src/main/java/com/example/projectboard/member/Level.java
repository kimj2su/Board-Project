package com.example.projectboard.member;

import com.example.projectboard.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Level extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private int postCount;

    private int likesCount;

    @Enumerated(EnumType.STRING)
    private MemberLevel memberLevel = MemberLevel.NORMAL;

    public Level() {
    }

    public Level(Member member, int postCount, int likesCount) {
        this.member = member;
        this.postCount = postCount;
        this.likesCount = likesCount;
    }

    public void levelUp(int postCount, int likesCount) {
        addPostCount(postCount);
        addLikesCount(likesCount);
        this.memberLevel = MemberLevel.getNextLevel(this.postCount + this.likesCount);
    }

    private void addPostCount(int postCount) {
        this.postCount += postCount;
    }

    private void addLikesCount(int likesCount) {
        this.likesCount += likesCount;
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public int getPostCount() {
        return postCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public MemberLevel getMemberLevel() {
        return memberLevel;
    }
}
