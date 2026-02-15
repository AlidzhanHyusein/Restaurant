package com.restaurant.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateMenuItemRequest {
    private String name;
    private BigDecimal price;
    private Long categoryId;
}