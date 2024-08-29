package com.example.projectboard.sse.application;

import com.example.projectboard.member.Member;
import com.example.projectboard.member.MemberRepository;
import com.example.projectboard.member.application.dto.MemberDto;
import com.example.projectboard.post.Post;
import com.example.projectboard.post.PostRepository;
import com.example.projectboard.support.error.ErrorType;
import com.example.projectboard.support.error.PostException;
import jakarta.transaction.Transactional;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class SseService {

  private static final Logger log = LoggerFactory.getLogger(SseService.class);
  private final static Long DEFAULT_TIMEOUT = 60L * 1000 * 60L; // 1시간
  private final static String SSE_NAME = "post";
  private final EmitterRepository emitterRepository;
  private final PostRepository postRepository;
  private final MemberRepository memberRepository;

  public SseService(EmitterRepository emitterRepository, PostRepository postRepository,
      MemberRepository memberRepository) {
    this.emitterRepository = emitterRepository;
    this.postRepository = postRepository;
    this.memberRepository = memberRepository;
  }

  @Transactional
  public void send(Long memberId, Long postId, String title, String content) {
    // modify
    Post post = validation(postId, memberId);
    post.modifyPost(title, content);
    postRepository.save(post);

    emitterRepository.getEmitter(memberId, post.getId()).ifPresentOrElse(sseEmitter -> {
      try {
        sseEmitter.send(
            SseEmitter.event().id(post.getId().toString()).name(SSE_NAME)
                .data("새로운 게시글이 등록되었습니다."));
      } catch (IOException e) {
        emitterRepository.delete(memberId, post.getId());
        throw new PostException(ErrorType.POST_SSE_ERROR, "SSE 전송 중 오류가 발생했습니다.");
      }
    }, () -> log.info("SseEmitter가 존재하지 않습니다."));
  }

  public SseEmitter connectPost(MemberDto memberDto, Long postId) {
    SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);
    emitterRepository.addEmitter(memberDto.id(), postId, sseEmitter);
    sseEmitter.onCompletion(() -> emitterRepository.delete(memberDto.id(), postId));
    sseEmitter.onTimeout(() -> emitterRepository.delete(memberDto.id(), postId));

    try {
      sseEmitter.send(SseEmitter.event().id("").name(SSE_NAME).data("연결 성공"));
    } catch (IOException e) {
      throw new PostException(ErrorType.POST_SSE_ERROR, "SSE 연결 중 오류가 발생했습니다.");
    }

    return sseEmitter;
  }

  private Post validation(Long id, Long memberId) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new PostException(ErrorType.POST_NOT_FOUND,
            String.format("%s, 게시글이 존재하지 않습니다.", id)));
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new PostException(ErrorType.MEMBER_NOT_FOUND_ERROR,
            String.format("%s, 회원이 존재하지 않습니다.", memberId)));
    post.validationMember(member);
    return post;
  }
}
