package com.example.logtracetest.sample.r2dbc;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@EnableR2dbcRepositories(basePackages = "com.example.logtracetest")
public class R2dbcConfig {
  @Bean
  public ConnectionFactory connectionFactory() {
    return  ConnectionFactories
        .get("r2dbc:pool:mariadb://bynddb:db4646bYnd@192.168.2.82:3306/" +
            "danjiservice?useUnicode=true&charaterEncoding=utf-8&serverTimezone=Asia/Seoul");
  }
}
