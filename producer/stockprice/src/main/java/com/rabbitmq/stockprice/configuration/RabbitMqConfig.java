package com.rabbitmq.stockprice.configuration;

import org.rabbitdomain.constants.RabbitMqConstants;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.rabbitdomain.constants.RabbitMqConstants.EXCHANGE_NAME;

@Configuration
public class RabbitMqConfig {

  // Define the exchange (Direct)
  @Bean
  public DirectExchange directExchange() {
    return new DirectExchange(EXCHANGE_NAME);
  }

  // Define Stock Queue
  @Bean
  public Queue stockQueue() {
    return QueueBuilder.durable(RabbitMqConstants.STOCK_QUEUE)
//        .exclusive()
        .build();
  }

  // Define Price Queue
  @Bean
  public Queue priceQueue() {
    return QueueBuilder.durable(RabbitMqConstants.PRICE_QUEUE)
        .build();
  }

  // Binding Stock Queue with an Exchange
  @Bean
  public Binding stockBinding(Queue stockQueue, DirectExchange directExchange) {
    return BindingBuilder
        .bind(stockQueue)
        .to(directExchange)
        .with(RabbitMqConstants.STOCK_QUEUE); // Routing Key same as queuename
  }

  // Binding Price Queue with an Exchange
  @Bean
  public Binding priceBinding(Queue priceQueue, DirectExchange directExchange) {
    return BindingBuilder
        .bind(priceQueue)
        .to(directExchange)
        .with(RabbitMqConstants.PRICE_QUEUE);
  }
}