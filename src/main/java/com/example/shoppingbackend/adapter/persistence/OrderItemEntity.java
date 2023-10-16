package com.example.shoppingbackend.adapter.persistence;

import com.example.shoppingbackend.domain.Order;
import com.example.shoppingbackend.domain.OrderItem;
import com.example.shoppingbackend.domain.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_items")
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    private Integer quantity;

    public Long getId() {
        return id;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public OrderItemEntity() {
    }

    public OrderItemEntity(ProductEntity product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public OrderItemEntity(OrderEntity order, ProductEntity product, int quantity) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }

}