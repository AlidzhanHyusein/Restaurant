package com.restaurant.repository;

import com.restaurant.entity.Order;
import com.restaurant.entity.OrderStatus;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomerId(Long id);

    List<Order> findByCustomerIdAndStatus(Long customerId, OrderStatus status);

    List<Order> findByCustomerIdAndStatusAfterOrderByCreatedAtDesc(Long customerId, OrderStatus orderStatusAfter);

    List<Order> findByStatusAndUpdatedAtBetween(OrderStatus status, LocalDateTime from, LocalDateTime to);

    List<Order> findByStatusOrderByCreatedAtDesc(OrderStatus status);

    long countByCustomerIdAndStatus(Long customerId, OrderStatus status);

    List<Order> findByStatusAndCreatedAtAfter(OrderStatus orderStatus, LocalDateTime since);
}

