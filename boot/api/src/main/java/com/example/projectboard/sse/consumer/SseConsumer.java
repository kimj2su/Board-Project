package com.example.projectboard.sse.consumer;

import com.example.projectboard.sse.application.SseService;
import com.example.projectboard.sse.event.SseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class SseConsumer {

  private static final Logger log = LoggerFactory.getLogger(SseConsumer.class);
  private final SseService sseService;

  public SseConsumer(SseService sseService) {
    this.sseService = sseService;
  }

  @KafkaListener(topics = "${spring.kafka.topic.sse}")
  public void consumeSse(SseEvent sseEvent, Acknowledgment ack) {
    log.info("Consume the event: {}", sseEvent);
    sseService.send(sseEvent.getMemberId(), sseEvent.getPostId(), sseEvent.getTitle(),
        sseEvent.getContent());
    ack.acknowledge();
  }
}
