///**
// *  Commented because was first contact with queues,
// *  used better approach with spring configuration and beans at
// *  src/main/java/com/rabbitmq/stockprice/configuration/RabbitMqConfig.java
// */
//
//package com.rabbitmq.stockprice.connections;
//
//import jakarta.annotation.PostConstruct;
//import org.rabbitdomain.constants.RabbitMqConstants;
//import org.springframework.amqp.core.AmqpAdmin;
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.stereotype.Component;
//
//import static org.rabbitdomain.constants.RabbitMqConstants.EXCHANGE_NAME;
//
//
//@Component
//public class RabbitMqConnection {
//
//  private AmqpAdmin amqpAdmin;
//
//  public RabbitMqConnection(AmqpAdmin amqpAdmin) {
//    this.amqpAdmin = amqpAdmin;
//  }
//
//  /**
//   * Initializing Queue
//   *
//   * @param queueName
//   * @return
//   */
//  private Queue queue(String queueName) {
//    return new Queue(queueName, true, false, false);
//  }
//
//  /**
//   * Creating Exchange on Rabbit
//   *
//   * @return
//   */
//  private DirectExchange directExchange() {
//    return new DirectExchange(EXCHANGE_NAME);
//  }
//
//  /**
//   * Relating a queue to an exchange
//   *
//   * @param queue
//   * @param directExchange
//   * @return
//   */
//  private Binding relateQueueAndExchange(Queue queue, DirectExchange directExchange) {
//    return new Binding(queue.getName(), Binding.DestinationType.QUEUE,
//        directExchange().getName(), queue.getName(), null);
//  }
//
//  /**
//   * Adding a queue to rabbitmq
//   *
//   */
//  @PostConstruct // Execute after spring starts and construct the component
//  private void addQueue() {
//    Queue stockQueue = this.queue(RabbitMqConstants.STOCK_QUEUE);
//    var priceQueue = this.queue(RabbitMqConstants.PRICE_QUEUE);
//
//    DirectExchange exchange = this.directExchange();
//
//    Binding stockBinding = this.relateQueueAndExchange(stockQueue, exchange);
//    var priceBinding = this.relateQueueAndExchange(priceQueue, exchange);
//
////    Creating queues on rabbitmq
//    amqpAdmin.declareQueue(stockQueue);
//    amqpAdmin.declareQueue(priceQueue);
//
//    amqpAdmin.declareExchange(exchange);
//
//    amqpAdmin.declareBinding(stockBinding);
//    amqpAdmin.declareBinding(priceBinding);
//
//
//
//  }
//}
