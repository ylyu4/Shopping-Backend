package com.example.shoppingbackend.domain;

import com.example.shoppingbackend.adapter.persistence.OrderItemEntity;

public class OrderItem {

    private Long id;

    private Product product;

    private Integer quantity;


    public Long getId() {
        return id;
    }


    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public OrderItem(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public OrderItem(OrderItemEntity entity) {
        this.id = entity.getId();
        this.product = new Product(entity.getProduct());
        this.quantity = entity.getQuantity();
    }
}
