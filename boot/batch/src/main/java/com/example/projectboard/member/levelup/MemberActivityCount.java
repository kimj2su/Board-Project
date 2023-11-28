package com.example.projectboard.member.levelup;

public class MemberActivityCount {

    private Long memberId;
    private Integer postCount;
    private Integer likesCount;

    public MemberActivityCount() {
    }

    public MemberActivityCount(Long memberId, Integer postCount, Integer likesCount) {
        this.memberId = memberId;
        this.postCount = postCount;
        this.likesCount = likesCount;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Integer getPostCount() {
        return postCount;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public void setPostCount(Integer postCount) {
        this.postCount = postCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    @Override
    public String toString() {
        return "MemberActivityCount{" +
                "memberId=" + memberId +
                ", postCount=" + postCount +
                ", likesCount=" + likesCount +
                '}';
    }
}
