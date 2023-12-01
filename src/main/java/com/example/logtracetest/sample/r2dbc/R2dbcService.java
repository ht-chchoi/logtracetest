package com.example.logtracetest.sample.r2dbc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class R2dbcService {
  private final SampleRepo sampleRepo;

  public Mono<SampleData> r2dbcSelectCall() {
    return this.sampleRepo.findById(8)
        .doOnNext(sampleData -> log.info("r2dbcSelectCall()"));
  }
}
