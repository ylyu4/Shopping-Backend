package com.example.shoppingbackend.application.port.out;


import com.example.shoppingbackend.adapter.in.request.CreateOrderRequest;

import java.util.List;

public interface SaveOrderPort {

    void saveOrder(Long id, List<CreateOrderRequest.OrderItemRequest> orderItemRequestList);

}
