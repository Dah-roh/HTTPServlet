package com.decagon.servletmvcsq019.models;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class Product {
    private Long id;
    private String name;
    private BigDecimal productPrice;

    private Long userId;
}
