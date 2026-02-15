package com.restaurant.service;

import com.restaurant.dto.CustomerRequest;
import com.restaurant.dto.OrderItemRequest;
import com.restaurant.entity.Customer;
import com.restaurant.entity.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    Order createOrder(Long customerId, List<OrderItemRequest> items);
    Order createOrderWithNewCustomer(CustomerRequest customer, List<OrderItemRequest> items);
    Order findById(Long id);
    List<Order> findByCustomerId(Long id);
    List<Order> findOnlyActiveOrdersByCustomerId(Long id);
    Order markOrderAsCompleted(Long id);
    void deleteById(Long id);
    List<Order> getAllActiveOrders();
    Order updateOrderItems(Long orderId, List<OrderItemRequest> items);
}
