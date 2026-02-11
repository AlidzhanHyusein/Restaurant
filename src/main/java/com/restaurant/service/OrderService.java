package com.restaurant.service;

import com.restaurant.entity.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(Order order);
    Order findById(Long id);
    List<Order> findByCustomerId(Long id);
    List<Order> findOnlyActiveOrdersByCustomerId(Long id);
    Order markOrderAsCompleted(Long id);
    void deleteById(Long id);
}
