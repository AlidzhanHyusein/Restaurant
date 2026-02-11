package com.restaurant.repository;

import com.restaurant.entity.Order;
import com.restaurant.entity.OrderStatus;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomerId(Long id);

    List<Order> findByCustomerIdAndStatus(Long customerId, OrderStatus status);

    List<Order> findByCustomerIdAndStatusAfterOrderByCreatedAtDesc(Long customerId, OrderStatus orderStatusAfter);

    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.customer.id = :customerId AND o.status = 'ACTIVE'")
    BigDecimal calculateTotalDebtByCustomerId(@Param("customerId") Long customerId);

    List<Order> findByStatusAfterOrderByCreatedAtDesc(OrderStatus status);

    long countByCustomerIdAndStatus(Long customerId, OrderStatus status);

}

