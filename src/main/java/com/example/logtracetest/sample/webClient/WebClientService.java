package com.example.logtracetest.sample.webClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebClientService {
  //  private final WebClient webClient = WebClient.builder().build();
  private final WebClient webClient;

  public Mono<HashMap> traceWebClient() {
    return webClient
        .get()
        .uri("http://localhost:8080/test")
        .retrieve()
        .bodyToMono(HashMap.class)
        .doOnNext(hashMap -> log.info("qweasd"));
  }
}
