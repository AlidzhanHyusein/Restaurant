package com.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
@Builder
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id",nullable = false)
    @JsonIgnoreProperties("orderItems")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "menu_item_id",nullable = false)
    @JsonIgnoreProperties("orderItem")
    private MenuItem menuItem;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false,precision = 10,scale = 2)
    private BigDecimal priceAtTime;

    @Column(nullable = false)
    private BigDecimal subtotal;


    public BigDecimal getSubtotal() {
        if (priceAtTime == null || quantity == null) {
            return BigDecimal.ZERO;
        }
        return priceAtTime.multiply(new BigDecimal(quantity));
    }
}
