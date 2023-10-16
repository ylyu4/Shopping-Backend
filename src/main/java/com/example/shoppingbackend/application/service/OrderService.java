package com.example.shoppingbackend.application.service;

import com.example.shoppingbackend.application.port.in.OrderUseCase;
import com.example.shoppingbackend.application.port.out.CustomerPort;
import com.example.shoppingbackend.application.port.out.OrderPort;
import com.example.shoppingbackend.application.port.out.ProductPort;
import com.example.shoppingbackend.adapter.in.command.CreateOrderCommand;
import com.example.shoppingbackend.adapter.out.response.CustomerOrdersResponse;
import com.example.shoppingbackend.adapter.out.response.OrderDetailsResponse;
import com.example.shoppingbackend.domain.Customer;
import com.example.shoppingbackend.domain.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements OrderUseCase {

    private final OrderPort orderPort;

    private final CustomerPort customerPort;

    private final ProductPort productPort;

    public OrderService(OrderPort orderPort,
                        CustomerPort customerPort,
                        ProductPort productPort) {
        this.orderPort = orderPort;
        this.customerPort = customerPort;
        this.productPort = productPort;
    }

    public void createOrder(CreateOrderCommand request) {
        orderPort.saveOrder(request.getCustomerId(), request.getOrderItemRequestList());
    }

    public CustomerOrdersResponse findAllOrderByCustomerId(Long id) {
        Customer customer = customerPort.getCustomerById(id);

        List<OrderDetailsResponse> orderDetailResponses = customer.getOrders().stream()
                .map(this::convertOrderToOrderDetailResponse).toList();
        return new CustomerOrdersResponse(orderDetailResponses);
    }

    public OrderDetailsResponse findOrderByOrderId(Long orderId) {
        Order order = orderPort.getOrderById(orderId);
        return convertOrderToOrderDetailResponse(order);
    }

    private OrderDetailsResponse convertOrderToOrderDetailResponse(Order order) {
        List<OrderDetailsResponse.OrderProductDetails> orderProductDetails = order.getOrderItems().stream()
                .map(orderItemEntity -> new OrderDetailsResponse.OrderProductDetails(orderItemEntity.getProduct().getName(),
                        orderItemEntity.getQuantity(), orderItemEntity.getProduct().getPrice())).toList();
        Integer totalPrice = orderProductDetails.stream()
                .mapToInt(orderItem -> orderItem.getPrice() * orderItem.getQuantity()).sum();
        return new OrderDetailsResponse(orderProductDetails, totalPrice, order.getOrderStatus());
    }
}
