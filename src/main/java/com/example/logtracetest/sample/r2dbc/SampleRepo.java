package com.example.logtracetest.sample.r2dbc;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SampleRepo extends ReactiveCrudRepository<SampleData, Integer> {
}
