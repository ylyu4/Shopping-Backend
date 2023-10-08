package com.example.shoppingbackend.model.response;

import java.util.List;

public class CustomerOrdersResponse {

    private List<OrderDetails> orders;

    public List<OrderDetails> getOrders() {
        return orders;
    }

    public CustomerOrdersResponse(List<OrderDetails> orders) {
        this.orders = orders;
    }
}
