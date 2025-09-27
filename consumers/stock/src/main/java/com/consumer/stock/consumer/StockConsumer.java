package com.consumer.stock.consumer;

import org.rabbitdomain.constants.RabbitMqConstants;
import org.rabbitdomain.dto.StockDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class StockConsumer {

  Logger logger = LoggerFactory.getLogger(StockConsumer.class);

  @RabbitListener(queues = RabbitMqConstants.STOCK_QUEUE)
  public void stockConsumer(StockDto stockDto) {
  logger.info("Received Message From Stock Queue: {}\n---------------", stockDto.toString());

  }
}
