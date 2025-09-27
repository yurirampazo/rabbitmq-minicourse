package com.consumer.stock.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.util.ErrorHandler;

public class ProcessingErrorHandler implements ErrorHandler {
  Logger logger = LoggerFactory.getLogger(ProcessingErrorHandler.class);

  @Override
  public void handleError(Throwable t) {
    var queueName = ((ListenerExecutionFailedException) t).getFailedMessage().getMessageProperties().getConsumerQueue();
    logger.info("Queue on error: {}", queueName);

    String message = new String(((ListenerExecutionFailedException) t).getFailedMessage().getBody());
    logger.info("Exception message: {}", message);

    logger.info("Init cause: {}", t.getCause().getMessage());

    // TODO: Log on  ElasticSearch
    //TODO: Log on Cloud Watch(AWS)

    throw new AmqpRejectAndDontRequeueException("Should not return to queue, infinite loop danger");
  }
}
