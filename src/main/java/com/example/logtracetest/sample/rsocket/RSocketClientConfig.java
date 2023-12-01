package com.example.logtracetest.sample.rsocket;

import io.rsocket.core.RSocketConnector;
import io.rsocket.metadata.WellKnownMimeType;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.instrument.rsocket.TracingRSocketConnectorConfigurer;
import org.springframework.cloud.sleuth.instrument.rsocket.TracingRequesterRSocketProxy;
import org.springframework.cloud.sleuth.propagation.Propagator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeType;
import reactor.util.retry.Retry;

import java.time.Duration;

@Configuration
public class RSocketClientConfig {

//  @Bean
//  public RSocketRequester rSocketRequester() {
//    return RSocketRequester.builder()
//        .rsocketStrategies(RSocketStrategies.builder()
//            .decoder(new Jackson2JsonDecoder())
//            .build())
//        .rsocketConnector(connector -> connector.reconnect(Retry.fixedDelay(2, Duration.ofSeconds(2))))
//        .dataMimeType(MimeType.valueOf("application/json"))
//        .transport(TcpClientTransport.create("localhost", 8000));
//  }

  @Bean
  public RSocketRequester rSocketRequester(Propagator propagator, Tracer tracer) {
    return RSocketRequester.builder()
        .rsocketStrategies(RSocketStrategies.builder()
            .encoder(new Jackson2JsonEncoder())
            .decoder(new Jackson2JsonDecoder())
            .build())
        .rsocketConnector(new TracingRSocketConnectorConfigurer(propagator, tracer, true))
        .dataMimeType(MimeType.valueOf("application/json"))
        .transport(TcpClientTransport.create("192.168.2.50", 9091));
  }
}
