package com.example.shoppingbackend.application.port.in;

import com.example.shoppingbackend.adapter.in.response.OrderDetailsResponse;

public interface GetOrderDetailUseCase {

    OrderDetailsResponse findOrderByOrderId(Long orderId);

}
