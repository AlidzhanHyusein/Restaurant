package com.restaurant.controller;

import com.restaurant.entity.Customer;
import com.restaurant.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/customerss")
@RequiredArgsConstructor
public class CustomerController {

    CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer){
        return ResponseEntity.ok(customerService.createCustomer(customer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> findCustomerById(@PathVariable Long id){
        return ResponseEntity.ok(customerService.findByCustomerById(id));
    }

    @GetMapping
    public ResponseEntity<Iterable<Customer>> findAllCustomers(){
        return ResponseEntity.ok(customerService.findAll());
    }

    @GetMapping("/{id}/debt")
    public ResponseEntity<BigDecimal> calculateTotalDebtByCustomerId(@PathVariable Long id){
        return ResponseEntity.ok(customerService.calculateTotalDebtByCustomerId(id));
    }

    @DeleteMapping("/{id}")
    public void deleteCustomerById(@PathVariable Long id){
        customerService.deleteById(id);
    }


}
