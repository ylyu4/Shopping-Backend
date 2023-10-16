package com.example.shoppingbackend.application.port.out;


import com.example.shoppingbackend.adapter.in.command.CreateOrderCommand;
import com.example.shoppingbackend.domain.Order;

import java.util.List;

public interface OrderPort {

    void saveOrder(Long id, List<CreateOrderCommand.OrderItemRequest> orderItemRequestList);

    Order getOrderById(Long id);

}
