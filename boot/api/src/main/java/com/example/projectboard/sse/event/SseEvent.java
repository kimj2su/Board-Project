package com.example.projectboard.sse.event;


public class SseEvent {

  private Long memberId;
  private Long postId;
  private String title;
  private String content;

  public SseEvent() {
  }

  public SseEvent(Long memberId, Long postId, String title, String content) {
    this.memberId = memberId;
    this.postId = postId;
    this.title = title;
    this.content = content;
  }

  public Long getMemberId() {
    return memberId;
  }

  public void setMemberId(Long memberId) {
    this.memberId = memberId;
  }

  public Long getPostId() {
    return postId;
  }

  public void setPostId(Long postId) {
    this.postId = postId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public String toString() {
    return "SseEvent{" +
        "memberId=" + memberId +
        ", postId=" + postId +
        ", title='" + title + '\'' +
        ", content='" + content + '\'' +
        '}';
  }
}
