package com.example.logtracetest.sample.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import reactor.kafka.receiver.KafkaReceiver;

@Slf4j
//@Component
@RequiredArgsConstructor
public class ReadyListener implements ApplicationListener<ApplicationReadyEvent> {
  private final KafkaReceiver<String, String> kafkaReceiver;

  @Override
  public void onApplicationEvent(final ApplicationReadyEvent event) {
    this.kafkaReceiver
        .receiveAutoAck()
        .concatMap(r -> r)
        .doOnNext(stringStringConsumerRecord -> {
          log.info("next....");
        })
        .onErrorContinue((e, o) -> log.error("error on consume", e))
        .subscribe(record -> log.info("topic: {}, key: {}, value: {}", record.topic(), record.key(), record.value()));
  }
}
