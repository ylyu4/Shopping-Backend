package com.example.shoppingbackend.application.port.in;

import com.example.shoppingbackend.adapter.in.command.CreateOrderCommand;

public interface CreateOrderUseCase {

    void createOrder(CreateOrderCommand request);

}
