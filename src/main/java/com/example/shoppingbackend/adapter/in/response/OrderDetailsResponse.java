package com.example.shoppingbackend.adapter.in.response;

import com.example.shoppingbackend.domain.constant.OrderStatus;

import java.util.List;

public class OrderDetailsResponse {

    private List<OrderProductDetails> orderProducts;

    private Integer totalPrice;

    private OrderStatus orderStatus;

    public List<OrderProductDetails> getOrderProducts() {
        return orderProducts;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public OrderDetailsResponse(List<OrderProductDetails> orderProducts, Integer totalPrice, OrderStatus orderStatus) {
        this.orderProducts = orderProducts;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
    }

    public OrderDetailsResponse() {
    }

    public static class OrderProductDetails {

        private String name;

        private Integer quantity;

        private Integer price;

        public String getName() {
            return name;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public Integer getPrice() {
            return price;
        }

        public OrderProductDetails(String name, Integer quantity, Integer price) {
            this.name = name;
            this.quantity = quantity;
            this.price = price;
        }
    }
}

