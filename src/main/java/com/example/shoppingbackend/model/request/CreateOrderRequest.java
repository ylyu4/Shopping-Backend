package com.example.shoppingbackend.model.request;

import java.util.List;

public class CreateOrderRequest {

    private List<OrderItemRequest> orderItemRequestList;

    public List<OrderItemRequest> getOrderItemRequestList() {
        return orderItemRequestList;
    }

    public void setOrderItemRequestList(List<OrderItemRequest> orderItemRequestList) {
        this.orderItemRequestList = orderItemRequestList;
    }

    public static class OrderItemRequest {

        private Long id;

        private Integer quantity;

        public Long getId() {
            return id;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }

}