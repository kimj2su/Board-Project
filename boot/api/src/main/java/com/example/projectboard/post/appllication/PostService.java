package com.example.projectboard.post.appllication;

import com.example.projectboard.member.Member;
import com.example.projectboard.member.application.dto.MemberDto;
import com.example.projectboard.post.Post;
import com.example.projectboard.post.PostRepository;
import com.example.projectboard.post.appllication.dto.PostDto;
import com.example.projectboard.sse.event.SseEvent;
import com.example.projectboard.sse.producer.SseProducer;
import com.example.projectboard.support.error.ErrorType;
import com.example.projectboard.support.error.PostException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostService {

  private final PostRepository postRepository;
  private final SseProducer sseProducer;

  public PostService(PostRepository postRepository, SseProducer sseProducer) {
    this.postRepository = postRepository;
    this.sseProducer = sseProducer;
  }

  @Transactional(readOnly = true)
  public PostDto findPost(Long id) {
    return postRepository.findById(id).map(PostDto::from)
        .orElseThrow(() -> new PostException(ErrorType.POST_NOT_FOUND,
            String.format("%s, 게시글이 존재하지 않습니다.", id)));
  }

  @Transactional(readOnly = true)
  public Page<PostDto> findAllPost(Pageable pageable) {
    return postRepository.findAll(pageable).map(PostDto::from);
  }

  public PostDto createPost(PostDto postDto) {
    Member member = postDto.memberDto().toEntity();
    Post post = postRepository.save(postDto.toEntity(member));
    return PostDto.from(post);
  }

  public void modifyPost(Long id, PostDto postDto) {
    sseProducer.send(
        new SseEvent(postDto.memberDto().id(), id, postDto.title(), postDto.content()));
  }

  public void deletePost(Long id, MemberDto memberDto) {
    Post post = validation(id, memberDto);
    post.deleted();
  }

  public void validationPost(Long id, MemberDto memberDto) {
    validation(id, memberDto);
  }

  private Post validation(Long id, MemberDto memberDto) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new PostException(ErrorType.POST_NOT_FOUND,
            String.format("%s, 게시글이 존재하지 않습니다.", id)));
    post.validationMember(memberDto.toEntity());
    return post;
  }
}
