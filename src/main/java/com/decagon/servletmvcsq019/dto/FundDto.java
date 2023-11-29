package com.decagon.servletmvcsq019.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FundDto {
    private Long id;
    private BigDecimal amount;

}
