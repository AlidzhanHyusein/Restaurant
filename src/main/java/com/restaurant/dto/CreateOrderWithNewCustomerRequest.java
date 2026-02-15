package com.restaurant.dto;

import lombok.Data;
import java.util.List;

@Data
public class CreateOrderWithNewCustomerRequest {
    private CustomerRequest customer;
    private List<OrderItemRequest> orderItems;
}