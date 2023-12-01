package com.example.logtracetest.sample.webflux;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class TraceRouter {
  private final TraceHandler traceHandler;

  @Bean
  public RouterFunction<ServerResponse> traceRouterFunc() {
    return route(GET("/trace/service"), traceHandler::traceService)
        .andRoute(POST("/trace/service"), traceHandler::traceService)
        .andRoute(DELETE("/device"), traceHandler::traceService)

        .andRoute(GET("/trace/r2dbc"), traceHandler::traceR2dbc)
        .andRoute(GET("/trace/webclient"), traceHandler::traceWebClient)
        .andRoute(GET("/trace/produce"), traceHandler::traceKafkaProduce)
        .andRoute(GET("/trace/rsocket"), traceHandler::traceRsocket);
  }
}
