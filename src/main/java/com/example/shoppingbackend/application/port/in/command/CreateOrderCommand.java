package com.example.shoppingbackend.application.port.in.command;

import java.util.List;

public class CreateOrderCommand {

    private Long customerId;

    private List<OrderItemRequest> orderItemRequestList;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

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
