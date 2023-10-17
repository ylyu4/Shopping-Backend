package com.example.shoppingbackend.application.port.in;

import com.example.shoppingbackend.adapter.in.response.CustomerOrdersResponse;

public interface GetAllOrdersUseCase {

    CustomerOrdersResponse findAllOrdersByCustomerId(Long id);

}
