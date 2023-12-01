package com.example.logtracetest.sample.rsocket;

import com.example.logtracetest.sample.webflux.SimpleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.util.function.Predicate;

@Controller
@RequiredArgsConstructor
@Slf4j
public class RSocketController {
  private final SimpleService simpleService;
  private final RSocketService rSocketService;

  @MessageMapping("/trace/service")
  public Mono<String> traceService() {
    log.info("traceController() call start");
    return this.simpleService.simpleMethod()
        .doOnNext(s -> log.info("traceController() call end"));
  }

  @MessageMapping("/trace/rsocket")
  public Mono<A> traceRSocket() {
    return this.rSocketService.traceRSocket();
  }

}
