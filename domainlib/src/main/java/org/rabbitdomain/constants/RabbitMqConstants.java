package org.rabbitdomain.constants;

public final class RabbitMqConstants {
  public static final String STOCK_QUEUE = "STOCK";
  public static final String PRICE_QUEUE = "PRICE";
  public static final String EXCHANGE_NAME = "amq.direct";
  public static final String DLX_EXCHANGE = "stockprice.dlx";
  public static final String STOCK_DLQ = "stock.dlq";
  public static final String PRICE_DLQ = "price.dlq";
  public static final String X_DLX = "x-dead-letter-exchange";
  public static final String X_DL_ROUTING_KEY = "x-dead-letter-routing-key";

  private RabbitMqConstants(){}
}
