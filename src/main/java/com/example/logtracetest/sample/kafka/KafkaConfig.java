package com.example.logtracetest.sample.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.cloud.sleuth.instrument.kafka.ReactiveKafkaTracingPropagator;
import org.springframework.cloud.sleuth.instrument.kafka.TracingKafkaConsumerFactory;
import org.springframework.cloud.sleuth.instrument.kafka.TracingKafkaProducerFactory;
import org.springframework.cloud.sleuth.instrument.kafka.TracingKafkaReceiver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerializer;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

  @Bean
  public KafkaSender<String, Object> kafkaSender(final BeanFactory beanFactory) {
    Map<String, Object> senderProps = new HashMap<>();
    senderProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.2.89:29091");
    senderProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    senderProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    senderProps.put("spring.json.add.type.headers", false);

    SenderOptions<String, Object> senderOptions = SenderOptions.create(senderProps);
    return KafkaSender.create(new TracingKafkaProducerFactory(beanFactory), senderOptions);
  }

//  @Bean
  public KafkaReceiver<String, String> kafkaReceiver(ReactiveKafkaTracingPropagator reactiveKafkaTracingPropagator) {
    Map<String, Object> consumerConfig = new HashMap<>();
    consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.2.89:29091");
    consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, "test-1");
    consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    consumerConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, OffsetResetStrategy.LATEST.name().toLowerCase());
    consumerConfig.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 10);

    ReceiverOptions<String, String> receiverOptions = ReceiverOptions.create(consumerConfig);

    return TracingKafkaReceiver
        .create(reactiveKafkaTracingPropagator, receiverOptions
            .atmostOnceCommitAheadSize(20)
            .subscription(Pattern.compile("chchoi.test.*"))
        );
  }
}
