package com.rabbitmq.stockprice.dto;

import java.io.Serializable;

public class StockDto implements Serializable {
  private String productCode;
  private Integer quantity;

  @Override
  public String toString() {
    return "StockDto{" +
        "productCode='" + productCode + '\'' +
        ", quantity=" + quantity +
        '}';
  }
}
