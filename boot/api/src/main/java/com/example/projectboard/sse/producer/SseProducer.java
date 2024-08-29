package com.example.projectboard.sse.producer;

import com.example.projectboard.sse.event.SseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SseProducer {

  private static final Logger log = LoggerFactory.getLogger(SseProducer.class);
  private final KafkaTemplate<Long, SseEvent> kafkaTemplate;

  @Value("${spring.kafka.topic.sse}")
  private String topic;

  public SseProducer(KafkaTemplate<Long, SseEvent> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void send(SseEvent sseEvent) {
    kafkaTemplate.send(topic, sseEvent.getMemberId(), sseEvent);
    log.info("Send to Kafka finished");
  }
}
