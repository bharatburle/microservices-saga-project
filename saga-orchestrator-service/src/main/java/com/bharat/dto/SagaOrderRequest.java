package com.bharat.dto;

import lombok.Data;

@Data
public class SagaOrderRequest {
    private String item;
    private int quantity;
    private double amount;
    private String address;
}
