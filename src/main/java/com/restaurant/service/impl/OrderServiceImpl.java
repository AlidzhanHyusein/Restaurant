package com.restaurant.service.impl;


import com.restaurant.entity.Customer;
import com.restaurant.entity.Order;
import com.restaurant.entity.OrderStatus;
import com.restaurant.repository.OrderRepository;
import com.restaurant.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerServiceImpl customerService;

    @Override
    public Order createOrder(Order order,Long customerId) {

        if(order.getTotalAmount().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Total amount cannot be negative or zero");
        }

        Customer customer = customerService.findByCustomerById(customerId);

        order.setCustomer(customer);
        order.setStatus(OrderStatus.ACTIVE);
        return orderRepository.save(order);
    }

    @Override
    public Order createOrder(Order order, Customer customer) {

        if(order.getTotalAmount().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Total amount cannot be negative or zero");
        }

        Customer customer1;
        if(customer.getId() == null){

            Customer customer2 = customerService.createCustomer(customer);
            order.setCustomer(customer2);
            order.setStatus(OrderStatus.ACTIVE);
            return orderRepository.save(order);
        } else{
            customer1 = customerService.findByCustomerById(customer.getId());

        }

        order.setCustomer(customer1);
        order.setStatus(OrderStatus.ACTIVE);
        return orderRepository.save(order);
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

    @Override
    public List<Order> getAllActiveOrders() {
        return orderRepository.findByStatusOrderByCreatedAtDesc(OrderStatus.ACTIVE);
    }
}
