package com.restaurant.service;

import com.restaurant.entity.Customer;
import com.restaurant.entity.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(Order order, Long customerId);
    Order createOrder(Order order, Customer customer);
    Order findById(Long id);
    List<Order> findByCustomerId(Long id);
    List<Order> findOnlyActiveOrdersByCustomerId(Long id);
    Order markOrderAsCompleted(Long id);
    void deleteById(Long id);
    List<Order> getAllActiveOrders();


}
