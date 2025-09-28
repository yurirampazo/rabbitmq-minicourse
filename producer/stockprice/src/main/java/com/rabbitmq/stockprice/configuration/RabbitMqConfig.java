package com.rabbitmq.stockprice.configuration;

import jakarta.annotation.PostConstruct;
import org.rabbitdomain.constants.RabbitMqConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.rabbitdomain.constants.RabbitMqConstants.*;

@Configuration
public class RabbitMqConfig {

  @Bean
  public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
    RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
    rabbitAdmin.setAutoStartup(true); // essential
    return rabbitAdmin;
  }

  // Define the exchange (Direct)
  @Bean
  public DirectExchange directExchange() {
    return new DirectExchange(EXCHANGE_NAME);
  }

  @Bean
  public DirectExchange directExchangeDlx() {
    return new DirectExchange(DLX_EXCHANGE);
  }

  // STOCK queue with DLX settings
  @Bean
  public Queue stockQueue() {
    return QueueBuilder.durable(RabbitMqConstants.STOCK_QUEUE)
//        .exclusive()
        .withArgument(X_DLX, DLX_EXCHANGE)
        .withArgument(X_DL_ROUTING_KEY, STOCK_DLQ)
        .build();
  }

  @Bean
  public Queue priceQueue() {
    return QueueBuilder.durable(RabbitMqConstants.PRICE_QUEUE)
        .withArgument(X_DLX, DLX_EXCHANGE)
        .withArgument(X_DL_ROUTING_KEY, PRICE_DLQ)
        .build();
  }

  // DLQs
  @Bean
  public Queue stockDlq() {
    return QueueBuilder.durable(STOCK_DLQ).build();
  }

  @Bean
  public Queue priceDlq() {
    return QueueBuilder.durable(PRICE_DLQ).build();
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

  // === DLQ bindings ===
  @Bean
  public Binding stockDlqBinding() {
    return BindingBuilder.bind(stockDlq())
        .to(directExchangeDlx())
        .with(STOCK_DLQ);
  }

  @Bean
  public Binding priceDlqBinding() {
    return BindingBuilder.bind(priceDlq())
        .to(directExchangeDlx())
        .with(PRICE_DLQ);
  }
}