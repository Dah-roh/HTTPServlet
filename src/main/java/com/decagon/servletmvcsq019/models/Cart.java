package com.decagon.servletmvcsq019.models;

import lombok.Data;

import java.util.List;

@Data
public class Cart {
    private Long id;
    private Long userId;
    private List<Long> productId;
}
