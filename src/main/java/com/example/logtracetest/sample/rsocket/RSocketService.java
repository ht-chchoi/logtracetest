package com.example.logtracetest.sample.rsocket;

import io.netty.buffer.ByteBuf;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.sleuth.annotation.ContinueSpan;
import org.springframework.cloud.sleuth.instrument.rsocket.TracingRSocketConnectorConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.Message;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class RSocketService {
  private final RSocketRequester rSocketRequester;

//  @ContinueSpan
  public Mono<A> traceRSocket() {
    log.info("traceRSocket() start");
//    return this.rSocketRequester
//        .route("test.1.test1")
//        .data(new TestObj("testasd"))
//        .retrieveMono(Map.class)
//        .doOnNext(a -> log.info("receiveFromRSocket: {}", a))
//        .map(a -> new A());

    return this.rSocketRequester
        .route("test.1.test1")
        .data(new TestObj("testasd"))
        .send()
        .then(Mono.fromCallable(A::new));
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class TestObj {
    private String value;
  }
}