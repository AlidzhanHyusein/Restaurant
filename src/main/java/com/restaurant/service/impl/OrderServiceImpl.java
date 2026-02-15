package com.restaurant.service.impl;


import com.restaurant.dto.CustomerRequest;
import com.restaurant.dto.OrderItemRequest;
import com.restaurant.entity.*;
import com.restaurant.repository.OrderRepository;
import com.restaurant.service.CustomerService;
import com.restaurant.service.MenuItemService;
import com.restaurant.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final MenuItemService menuItemService;

    @Override
    public Order createOrder(Long customerId, List<OrderItemRequest> items) {

        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order cannot be empty");
        }

        Customer customer = customerService.findByCustomerById(customerId);

        Order order = Order.builder()
                .customer(customer)
                .status(OrderStatus.ACTIVE)
                .build();

        for (OrderItemRequest itemRequest : items) {
            if (itemRequest.getQuantity() == null || itemRequest.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantity must be positive");
            }

            MenuItem menuItem = menuItemService.findMenuItemById(itemRequest.getMenuItemId());

            OrderItem orderItem = OrderItem.builder()
                    .menuItem(menuItem)
                    .quantity(itemRequest.getQuantity())
                    .priceAtTime(menuItem.getPrice())
                    .order(order)
                    .build();

            order.getOrderItems().add(orderItem);
        }

        return orderRepository.save(order);
    }

    @Override
    public Order updateOrderItems(Long orderId, List<OrderItemRequest> items) {
        Order order = findById(orderId);

        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order must have at least one item");
        }

        order.getOrderItems().clear();

        for (OrderItemRequest itemRequest : items) {
            if (itemRequest.getQuantity() == null || itemRequest.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantity must be positive");
            }

            MenuItem menuItem = menuItemService.findMenuItemById(itemRequest.getMenuItemId());

            OrderItem orderItem = OrderItem.builder()
                    .menuItem(menuItem)
                    .quantity(itemRequest.getQuantity())
                    .priceAtTime(menuItem.getPrice())
                    .order(order)
                    .build();

            order.getOrderItems().add(orderItem);
        }

        return orderRepository.save(order);
    }



    @Override
    public Order createOrderWithNewCustomer(CustomerRequest customerRequest,
                                            List<OrderItemRequest> items) {

        if (customerRequest == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }

        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order cannot be empty");
        }

        Customer savedCustomer = customerService.createCustomer(customerRequest);

        return createOrder(savedCustomer.getId(), items);
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
