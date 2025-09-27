package com.consumer.stock.configuration;

import com.consumer.stock.consumer.CustomErrorStrategy;
import com.consumer.stock.exception.ProcessingErrorHandler;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.DirectRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import org.springframework.amqp.rabbit.listener.FatalExceptionStrategy;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;

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

  @Bean
  public RabbitListenerContainerFactory<DirectMessageListenerContainer> rabbitListenerContainerFactory(
      ConnectionFactory connectionFactory
  ) {
    DirectRabbitListenerContainerFactory factory = new DirectRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
    factory.setPrefetchCount(3);
    factory.setRetryTemplate(retryTemplate());
    factory.setConsumersPerQueue(1);
    //factory.setGlobalQos(true);

    // Using ErrorHandler
//     factory.setErrorHandler(new ProcessingErrorHandler());

    // Using FatalStrategy implementation
    factory.setErrorHandler(errorHandler());

    return factory;
  }

  @Bean
  public FatalExceptionStrategy customErrorStrategy(){
    return new CustomErrorStrategy();
  }

  @Bean
  public ConditionalRejectingErrorHandler errorHandler() {
    return new ConditionalRejectingErrorHandler(customErrorStrategy());
  }

  @Bean
  public RetryTemplate retryTemplate() {
    final int MAX_ATTEMPTS = 3;
    return RetryTemplate.builder()
        .maxAttempts(MAX_ATTEMPTS)
        .retryOn(error -> !customErrorStrategy().isFatal(error))
        .build();
  }
}