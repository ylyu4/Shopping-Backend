package com.example.shoppingbackend.application.port.in;

import com.example.shoppingbackend.application.port.in.command.CreateOrderCommand;
import com.example.shoppingbackend.application.port.out.response.CustomerOrdersResponse;
import com.example.shoppingbackend.application.port.out.response.OrderDetailsResponse;

public interface OrderUseCase {

    void createOrder(CreateOrderCommand request);

    CustomerOrdersResponse findAllOrderByCustomerId(Long id);

    OrderDetailsResponse findOrderByOrderId(Long orderId);

}