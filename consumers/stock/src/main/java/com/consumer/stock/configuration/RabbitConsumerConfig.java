package com.consumer.stock.configuration;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Using Json approach with rabbit because of debbug preference
 * If needed less latency, maybe go for the byte serializable approach with Implementation of serializable interface
 * in the classes, but they need to be the same object, included package, so it's highly recommended to use a domain
 * lob for DTOs
 */
@Configuration
public class RabbitConsumerConfig {

  @Bean
  public MessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }
}