package com.rabbitmq.stockprice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqService {

  private final RabbitTemplate rabbitTemplate;
  private final ObjectMapper objectMapper;

  public RabbitMqService(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
    this.rabbitTemplate = rabbitTemplate;
    this.objectMapper = objectMapper;
  }

  public void sendMessage(String queueName, Object message) {
    try {
      var jsonMessage = objectMapper.writeValueAsString(message);
      rabbitTemplate.convertAndSend(queueName, message);
    } catch (Exception e) {
      throw new RuntimeException("Serializing error", e);
    }
  }
}
