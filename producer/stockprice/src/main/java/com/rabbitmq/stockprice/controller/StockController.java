package com.rabbitmq.stockprice.controller;

import com.rabbitmq.stockprice.constants.RabbitMqConstants;
import com.rabbitmq.stockprice.dto.StockDto;
import com.rabbitmq.stockprice.service.RabbitMqService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/stock")
public class StockController {
  private static final Logger logger = LoggerFactory.getLogger(StockController.class);

  @Autowired
  private RabbitMqService rabbitMqService;

  @PutMapping
  private ResponseEntity<Void> updateStock(@RequestBody StockDto stockDto) {
    logger.info(stockDto.toString());
    rabbitMqService.sendMessage(RabbitMqConstants.STOCK_QUEUE, stockDto);
    return ResponseEntity.ok().build();
  }

}
