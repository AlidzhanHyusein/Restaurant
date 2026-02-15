package com.restaurant.dto;

import com.restaurant.entity.MenuItem;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemRequest {
    private Long menuItemId;
    private MenuItem menuItem;
    private Integer quantity;
    private Double priceAtTime;
}