package com.rabbitmq.stockprice.connections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class RabbitAdminInitializer implements ApplicationListener<ContextRefreshedEvent> {

  private final Logger logger = LoggerFactory.getLogger(RabbitAdminInitializer.class);

  private final AmqpAdmin amqpAdmin;

  public RabbitAdminInitializer(AmqpAdmin amqpAdmin) {
    this.amqpAdmin = amqpAdmin;
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    logger.info("Forcing RabbitAdmin.initialize()");
    amqpAdmin.initialize();
  }
}
