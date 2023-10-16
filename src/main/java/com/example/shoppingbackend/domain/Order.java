package com.example.shoppingbackend.domain;

import com.example.shoppingbackend.adapter.persistence.OrderEntity;
import com.example.shoppingbackend.constant.OrderStatus;

import java.util.List;

public class Order {
    private Long id;

    private List<OrderItem> orderItems;

    private OrderStatus orderStatus;

    public Long getId() {
        return id;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Order() {
        this.orderStatus = OrderStatus.CREATED;
    }

    public Order(OrderEntity entity) {
        this.id = entity.getId();
        this.orderItems = entity.getOrderItems().stream().map(OrderItem::new).toList();
        this.orderStatus = entity.getOrderStatus();
    }
}