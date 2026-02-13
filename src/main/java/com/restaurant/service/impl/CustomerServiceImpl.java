package com.restaurant.service.impl;

import com.restaurant.entity.Customer;
import com.restaurant.entity.OrderStatus;
import com.restaurant.repository.CustomerRepository;
import com.restaurant.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(Customer customer) {
        if(customer == null){
            throw new IllegalArgumentException("Customer cannot be null");
        }
        return customerRepository.save(customer);
    }

    @Override
    public Customer findByCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Customer not found"));
    }

    @Override
    public Customer findByCustomerByName(String name) {
        Customer customer = customerRepository.findByName(name);
        if(customer == null || customer.getName() == null){
            throw new IllegalArgumentException("Customer not found");
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public BigDecimal calculateTotalDebtByCustomerId(Long customerId) {
        Customer customer = findByCustomerById(customerId);

        List<BigDecimal> totalAmount = customer.getOrder().stream().map(order -> order.getStatus() == OrderStatus.ACTIVE ? order.calculateTotalAmount() : BigDecimal.ZERO).toList();

        return totalAmount.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public void deleteById(Long id) {

        Customer customer = findByCustomerById(id);
        customerRepository.delete(customer);
    }
}
