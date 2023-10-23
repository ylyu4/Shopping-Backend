package com.example.shoppingbackend.adapter.persistence;

import com.example.shoppingbackend.domain.constant.OrderStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", orphanRemoval = true)
    private List<OrderItemEntity> orderItemEntities = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    public Long getId() {
        return id;
    }

    public List<OrderItemEntity> getOrderItems() {
        return orderItemEntities;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void addOrderItem(ProductEntity product, int quantity) {
        OrderItemEntity orderItemEntity = new OrderItemEntity(this, product, quantity);
        orderItemEntities.add(orderItemEntity);
    }

    public OrderEntity(CustomerEntity customer) {
        this.customer = customer;
        this.orderStatus = OrderStatus.CREATED;
    }

    public OrderEntity(){
    }

}
