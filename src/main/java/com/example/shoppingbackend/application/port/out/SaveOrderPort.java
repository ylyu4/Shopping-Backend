package com.example.shoppingbackend.application.port.out;


import com.example.shoppingbackend.adapter.in.command.CreateOrderCommand;

import java.util.List;

public interface SaveOrderPort {

    void saveOrder(Long id, List<CreateOrderCommand.OrderItemRequest> orderItemRequestList);

}
