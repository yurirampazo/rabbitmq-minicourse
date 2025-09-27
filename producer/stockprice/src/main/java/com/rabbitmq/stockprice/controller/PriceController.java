package com.rabbitmq.stockprice.controller;


import com.rabbitmq.stockprice.service.RabbitMqService;
import org.rabbitdomain.constants.RabbitMqConstants;
import org.rabbitdomain.dto.PriceDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/price")
public class PriceController {
  private static final Logger logger = LoggerFactory.getLogger(PriceController.class);

  @Autowired
  private RabbitMqService rabbitMqService;

  @PutMapping
  public ResponseEntity<Void> updatePrice(@RequestBody PriceDto priceDto) {
    rabbitMqService.sendMessage(RabbitMqConstants.PRICE_QUEUE, priceDto);
    return ResponseEntity.ok().build();
  }
}
