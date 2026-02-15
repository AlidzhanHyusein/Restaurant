package com.restaurant.service;

import com.restaurant.dto.CustomerRequest;
import com.restaurant.entity.Customer;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerService {

    Customer createCustomer(Customer customer);
    Customer findByCustomerById(Long id);
    Customer findByCustomerByName(String name);
    List<Customer> findAll();
    BigDecimal calculateTotalDebtByCustomerId(Long customerId);
    void deleteById(Long id);
    Customer createCustomer(CustomerRequest request);
    Customer getCustomerById(Long id);

}
