package com.restaurant.controller;

import com.restaurant.entity.Customer;
import com.restaurant.entity.Order;
import com.restaurant.service.CustomerService;
import com.restaurant.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    CustomerService customerService;
    OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(Order order, Long customerId){
        return ResponseEntity.ok(orderService.createOrder(order,customerId));
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(Order order, @RequestBody Customer customer){
        return ResponseEntity.ok(orderService.createOrder(order,customer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findOrderById(@PathVariable Long id){
        return ResponseEntity.ok(orderService.findById(id));
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Order> markOrderAsCompleted(@PathVariable Long id){
        return ResponseEntity.ok(orderService.markOrderAsCompleted(id));
    }

    @GetMapping
    public ResponseEntity<List<Order>> findAllOrdersByActiveStatus(){
        return ResponseEntity.ok(orderService.getAllActiveOrders());
    }

    @DeleteMapping("/{id}")
    public void deleteOrderById(@PathVariable Long id){
        orderService.deleteById(id);
    }

}
