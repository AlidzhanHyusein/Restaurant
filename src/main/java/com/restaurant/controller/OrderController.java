package com.restaurant.controller;

import com.restaurant.dto.CreateOrderRequest;
import com.restaurant.dto.CreateOrderWithNewCustomerRequest;
import com.restaurant.dto.OrderItemRequest;
import com.restaurant.entity.*;
import com.restaurant.repository.MenuItemRepository;
import com.restaurant.repository.OrderRepository;
import com.restaurant.service.CustomerService;
import com.restaurant.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final MenuItemRepository menuItemRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest request) {
        Customer customer = customerService.getCustomerById(request.getCustomerId());
        Order order = new Order();
        order.setCustomer(customer);
        order.setStatus(OrderStatus.ACTIVE);

        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemRequest itemRequest : request.getOrderItems()) {
            MenuItem menuItem = menuItemRepository.findById(itemRequest.getMenuItemId())
                    .orElseThrow(() -> new IllegalArgumentException("Menu item not found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(itemRequest.getQuantity());

            BigDecimal priceAtTime = BigDecimal.valueOf(itemRequest.getPriceAtTime());
            orderItem.setPriceAtTime(priceAtTime);

            BigDecimal subtotal = priceAtTime.multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            orderItem.setSubtotal(subtotal);

            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        Order saved = orderRepository.save(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PostMapping("/new-customer")
    @Transactional
    public ResponseEntity<Order> createOrderWithNewCustomer(@RequestBody Map<String, Object> request) {
        try {
            Map<String, Object> customerData = (Map<String, Object>) request.get("customer");
            Customer customer = new Customer();
            customer.setName(customerData.get("name").toString());

            Customer savedCustomer = customerService.createCustomer(customer);

            Order order = new Order();
            order.setCustomer(savedCustomer);
            order.setStatus(OrderStatus.ACTIVE);

            List<Map<String, Object>> orderItemsData = (List<Map<String, Object>>) request.get("orderItems");
            List<OrderItem> orderItems = new ArrayList<>();

            for (Map<String, Object> itemData : orderItemsData) {
                Map<String, Object> menuItemData = (Map<String, Object>) itemData.get("menuItem");
                Long menuItemId = Long.parseLong(menuItemData.get("id").toString());


                MenuItem menuItem = menuItemRepository.findById(menuItemId)
                        .orElseThrow(() -> new IllegalArgumentException("Menu item not found: " + menuItemId));

                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setMenuItem(menuItem);
                orderItem.setQuantity(((Number) itemData.get("quantity")).intValue());


                double priceDouble = ((Number) itemData.get("priceAtTime")).doubleValue();
                BigDecimal priceAtTime = BigDecimal.valueOf(priceDouble);
                orderItem.setPriceAtTime(priceAtTime);

                BigDecimal subtotal = priceAtTime.multiply(BigDecimal.valueOf(orderItem.getQuantity()));
                orderItem.setSubtotal(subtotal);

                orderItems.add(orderItem);
            }

            order.setOrderItems(orderItems);
            Order saved = orderRepository.save(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create order with new customer: " + e.getMessage(), e);
        }
    }

    @PutMapping("/{orderId}")
    @Transactional
    public ResponseEntity<Order> updateOrder(
            @PathVariable Long orderId,
            @RequestBody List<OrderItemRequest> items) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        order.getOrderItems().clear();

        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemRequest itemRequest : items) {
            MenuItem menuItem = menuItemRepository.findById(itemRequest.getMenuItemId())
                    .orElseThrow(() -> new IllegalArgumentException("Menu item not found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(itemRequest.getQuantity());

            BigDecimal priceAtTime = BigDecimal.valueOf(itemRequest.getPriceAtTime());
            orderItem.setPriceAtTime(priceAtTime);

            BigDecimal subtotal = priceAtTime.multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            orderItem.setSubtotal(subtotal);

            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        Order updated = orderRepository.save(order);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findOrderById(@PathVariable Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        return ResponseEntity.ok(order);
    }

    @PatchMapping("/{id}/complete")
    @Transactional
    public ResponseEntity<Order> markOrderAsCompleted(@PathVariable Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setStatus(OrderStatus.COMPLETED);
        Order updated = orderRepository.save(order);
        return ResponseEntity.ok(updated);
    }

    @GetMapping
    public ResponseEntity<List<Order>> findAllOrdersByActiveStatus(){
        return ResponseEntity.ok(orderRepository.findAll());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteOrderById(@PathVariable Long id){
        if (!orderRepository.existsById(id)) {
            throw new IllegalArgumentException("Order not found");
        }
        orderRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}