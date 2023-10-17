package com.example.shoppingbackend.application.port.out;

import com.example.shoppingbackend.domain.Order;

public interface GetOrderDetailPort {

    Order getOrderById(Long id);

}
