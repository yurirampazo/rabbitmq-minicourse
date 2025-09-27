package com.rabbitmq.stockprice.dto;

import java.io.Serializable;

public class PriceDto implements Serializable {
  private String productCode;
  private Double price;
}
