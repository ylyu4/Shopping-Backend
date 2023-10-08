package com.example.shoppingbackend.model.response;

import com.example.shoppingbackend.constant.OrderStatus;

import java.util.List;

public class OrderDetails {

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

    public OrderDetails(List<OrderProductDetails> orderProducts, Integer totalPrice, OrderStatus orderStatus) {
        this.orderProducts = orderProducts;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
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

