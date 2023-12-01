package com.example.logtracetest.sample.webflux;

import brave.Span;
import brave.Tracer;
import brave.Tracing;
import com.example.logtracetest.sample.kafka.KafkaService;
import com.example.logtracetest.sample.r2dbc.R2dbcService;
import com.example.logtracetest.sample.rsocket.RSocketService;
import com.example.logtracetest.sample.webClient.WebClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class TraceHandler {
  private final SimpleService simpleService;
  private final R2dbcService r2dbcService;
  private final WebClientService webClientService;
  private final KafkaService kafkaService;
  private final RSocketService rSocketService;
  private final Tracer tracer;
  private final Tracing tracing;

  public Mono<ServerResponse> traceService(final ServerRequest serverRequest) {
//    tracing.propagation().injector().inject();
//    log.info("current span: {}", tr);
    log.info("traceService() start");
    return simpleService.simpleMethod()
        .flatMap(s -> ServerResponse.ok().bodyValue(Map.of("time", s)))
        .doOnNext(serverResponse -> log.info("traceService() end"));
  }

  public Mono<ServerResponse> traceR2dbc(final ServerRequest serverRequest) {
    log.info("traceService() start");
    return this.r2dbcService.r2dbcSelectCall()
        .flatMap(sampleData -> ServerResponse.ok().bodyValue(sampleData))
        .doOnNext(serverResponse -> log.info("traceService() end"));
  }
  public Mono<ServerResponse> traceWebClient(final ServerRequest serverRequest) {
    log.info("traceService() start");
    return this.webClientService
        .traceWebClient()
        .flatMap(sampleData -> ServerResponse.ok().bodyValue(sampleData))
        .doOnNext(serverResponse -> log.info("traceService() end"));
  }

  public Mono<ServerResponse> traceKafkaProduce(final ServerRequest serverRequest) {
    log.info("traceService() start");
    return this.kafkaService
        .sendMessage(3)
        .collectList()
        .flatMap(sampleData -> ServerResponse.ok().bodyValue(sampleData))
        .doOnNext(serverResponse -> log.info("traceService() end"));
  }

  public Mono<ServerResponse> traceRsocket(final ServerRequest serverRequest) {
    log.info("traceService() start");
    return this.rSocketService
        .traceRSocket()
        .flatMap(sampleData -> ServerResponse.ok().bodyValue(sampleData))
        .doOnNext(serverResponse -> log.info("traceService() end"));
  }
}
