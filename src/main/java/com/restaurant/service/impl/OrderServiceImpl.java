package com.restaurant.service.impl;


import com.restaurant.entity.Customer;
import com.restaurant.entity.Order;
import com.restaurant.entity.OrderStatus;
import com.restaurant.repository.OrderRepository;
import com.restaurant.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerServiceImpl customerService;

    @Override
    public Order createOrder(Order order) {
        Order orderToSave = orderRepository.save(order);
        orderToSave.setCustomer(customerService.findByCustomerById(order.getCustomer().getId()));
        return orderToSave;
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("There is no order with this id: " + id));
    }

    @Override
    public List<Order> findByCustomerId(Long id) {
        return orderRepository.findByCustomerId(id);
    }

    @Override
    public List<Order> findOnlyActiveOrdersByCustomerId(Long id) {
        Customer customer = customerService.findByCustomerById(id);

        return orderRepository.findByCustomerIdAndStatus(customer.getId(), OrderStatus.ACTIVE);
    }

    @Override
    public Order markOrderAsCompleted(Long id) {
        Order order = findById(id);
        order.setStatus(OrderStatus.COMPLETED);
        return orderRepository.save(order);
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }
}
