package com.example.logtracetest.sample.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;
import reactor.kafka.sender.SenderResult;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaService {
  private final KafkaSender<String, Object> kafkaSender;

  @PostConstruct
  public void init() {
    this.sendMessage(10).subscribe();
//    this.sendDeviceCommand().subscribe();
  }

  public Flux<Integer> sendMessage(final int count) {

    return this.kafkaSender
        .send(Flux
            .range(1, count)
            .map(i -> {
              ProducerRecord<String, Object> record =
                  new ProducerRecord<>(
                      "chchoi.test.2",
                      String.valueOf(i),
                      (Object) ("{\"id\":\"xxx\",\"message\":\"test\"}"));
//              record.headers().add("__TypeId__", "com.ht.iot.domain.event.topic.data.TestTopic$Value".getBytes());
              record.headers().add("__TypeId__", "".getBytes());
              return SenderRecord.create(record, i);
            }))
        .doOnError(e -> log.error("send error", e))
        .doOnNext(response -> log.info("response: cm: {}, rm: {}", response.correlationMetadata(), response.recordMetadata()))
        .map(SenderResult::correlationMetadata);
  }

  public Flux<Integer> sendDeviceCommand() {
    return this.kafkaSender
        .send(Mono.just(
            SenderRecord.create(
                new ProducerRecord<>(
                    "device.command",
                    null,
                    null,
                    null,
                    Map.of(
                        "deviceInfo", Map.of(
                            "deviceId", "e230568074d1436082d04a55dbe39d2e",
                            "parentDeviceId", "64c9ba63fe70433295cbaadefe89d517",
                            "accessType", "DCH",
                            "accessInfo", Map.of(
                                "ip", "192.168.2.82",
                                "port", 40002
                            )
                        ),
                        "states", List.of(Map.of(
                            "name", "power",
                            "value", "off"
                        )),
                        "trigger", Map.of(
                            "type", "external",
                            "userId", "testUser1"
                        )
                    ),
                    new RecordHeaders()
//                        .add("b3", "3bce497d9c6833a4-89438a1ea2f740bf-0".getBytes())
                        .add("issuer", "AFE".getBytes())
                        .add("issuedTime", "1694409256000".getBytes())
                ),
                1
            )
        ))
        .map(SenderResult::correlationMetadata);
  }
}

