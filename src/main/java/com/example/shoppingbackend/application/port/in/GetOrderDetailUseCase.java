package com.example.shoppingbackend.application.port.in;

import com.example.shoppingbackend.adapter.out.response.OrderDetailsResponse;

public interface GetOrderDetailUseCase {

    OrderDetailsResponse findOrderByOrderId(Long orderId);

}
