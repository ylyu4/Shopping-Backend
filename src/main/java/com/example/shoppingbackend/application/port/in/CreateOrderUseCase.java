package com.example.shoppingbackend.application.port.in;

import com.example.shoppingbackend.adapter.in.request.CreateOrderRequest;

public interface CreateOrderUseCase {

    void createOrder(CreateOrderRequest request);

}
