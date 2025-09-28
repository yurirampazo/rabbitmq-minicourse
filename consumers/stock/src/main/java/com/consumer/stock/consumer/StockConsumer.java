package com.consumer.stock.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.rabbitdomain.constants.RabbitMqConstants;
import org.rabbitdomain.dto.StockDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpIOException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class StockConsumer {

  Logger logger = LoggerFactory.getLogger(StockConsumer.class);

  @RabbitListener(queues = RabbitMqConstants.STOCK_QUEUE)
  public void stockConsumer(StockDto stockDto) {
    logger.info("Received Message From Stock Queue: {}\n---------------", stockDto.toString());

    //TODO Do something with the message!

    //TEST DLQ
    throw new IllegalArgumentException("Going to DLQ!");
  }

  @RabbitListener(queues = RabbitMqConstants.STOCK_DLQ)
  public void escutaDLQ(Message message) {
    logger.info("Received Message in DLQ");
    var body = new String(message.getBody(), StandardCharsets.UTF_8);
    logger.info("Broker received message in STOCK_DLQ {}", body);
    var mapper = new ObjectMapper();
    StockDto stockDto;
    try {
      stockDto = mapper.readValue(body, StockDto.class);
    } catch (JsonProcessingException e) {
      throw new AmqpIOException("Error mapping DLQ message", e);
    }
    logger.info("Saving message with error: {}", stockDto);
    //TODO do another backup plan, reprocess, save in memory or database
  }
}
