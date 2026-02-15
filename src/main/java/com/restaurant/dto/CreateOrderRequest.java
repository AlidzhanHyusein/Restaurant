package com.restaurant.dto;

import lombok.Data;
import java.util.List;

@Data
public class CreateOrderRequest {
    private Long customerId;
    private List<OrderItemRequest> orderItems;
}