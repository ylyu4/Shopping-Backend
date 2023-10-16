package com.example.shoppingbackend.adapter.out.response;

import java.util.List;

public class CustomerOrdersResponse {

    private List<OrderDetailsResponse> orders;

    public List<OrderDetailsResponse> getOrders() {
        return orders;
    }

    public CustomerOrdersResponse(List<OrderDetailsResponse> orders) {
        this.orders = orders;
    }
}
