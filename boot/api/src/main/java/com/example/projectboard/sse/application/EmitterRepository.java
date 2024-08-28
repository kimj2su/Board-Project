package com.example.projectboard.sse.application;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@Repository
public class EmitterRepository {

  private static final Logger log = LoggerFactory.getLogger(EmitterRepository.class);
  private final Map<String, SseEmitter> emitterMap = new HashMap<>();

  public SseEmitter addEmitter(Long userId, Long postId, SseEmitter sseEmitter) {
    final String key = getKey(userId, postId);
    emitterMap.put(key, sseEmitter);
    log.info("Set sseEmitter {}", userId);
    log.info("Set sseEmitter {}", postId);
    return sseEmitter;
  }

  public Optional<SseEmitter> getEmitter(Long userId, Long postId) {
    final String key = getKey(userId, postId);
    log.info("Get sseEmitter {}", userId);
    log.info("Get sseEmitter {}", postId);
    return Optional.ofNullable(emitterMap.get(key));
  }

  public void delete(Long userId, Long postId) {
    final String key = getKey(userId, postId);
    emitterMap.remove(key);
    log.info("Delete sseEmitter {}", userId);
    log.info("Delete sseEmitter {}", postId);
  }

  public String getKey(Long userId, Long postId) {
    return "Emitter:UID:" + userId + ":PID:" + postId;
  }
}
