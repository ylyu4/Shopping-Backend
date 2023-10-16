package com.example.shoppingbackend.domain;

import com.example.shoppingbackend.adapter.persistence.CustomerEntity;
import java.util.List;

public class Customer {

    private Long id;

    private String name;

    private List<Order> orders;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Customer(CustomerEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.orders = entity.getOrders().stream().map(Order::new).toList();
    }

    public Customer(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
