package com.decagon.servletmvcsq019.models;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Order {
    private Long id;
    private String productIds;
    private BigDecimal totalPrice;
}
