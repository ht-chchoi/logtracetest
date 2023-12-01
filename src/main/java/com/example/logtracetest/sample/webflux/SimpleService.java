package com.example.logtracetest.sample.webflux;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class SimpleService {
  public Mono<String> simpleMethod() {
    return Flux.fromIterable(List.of(1,2,3,4,5))
        .publishOn(Schedulers.newBoundedElastic(1, 1, "newTaskRunner"))
        .doOnNext(i -> log.info("simpleMethod(), {}", i))
        .collectList()
        .map(o -> LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
  }
}
