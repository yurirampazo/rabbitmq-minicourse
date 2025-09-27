package com.consumer.stock.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;

public class CustomErrorStrategy extends ConditionalRejectingErrorHandler.DefaultExceptionStrategy {

  private final Logger logger = LoggerFactory.getLogger(CustomErrorStrategy.class);

  @Override
  public boolean isFatal(Throwable t) {
    var error = (ListenerExecutionFailedException) t;
    logger.info(error.getFailedMessage().getBody().toString());

    return t.getCause() instanceof IllegalArgumentException;
  }
}
