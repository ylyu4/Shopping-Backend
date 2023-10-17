package com.example.shoppingbackend.application.service;

import com.example.shoppingbackend.application.port.in.CreateOrderUseCase;
import com.example.shoppingbackend.application.port.in.GetAllOrdersUseCase;
import com.example.shoppingbackend.application.port.in.GetOrderDetailUseCase;
import com.example.shoppingbackend.application.port.out.GetCustomerProfilePort;
import com.example.shoppingbackend.application.port.out.GetOrderDetailPort;
import com.example.shoppingbackend.application.port.out.SaveOrderPort;
import com.example.shoppingbackend.adapter.in.command.CreateOrderCommand;
import com.example.shoppingbackend.adapter.out.response.CustomerOrdersResponse;
import com.example.shoppingbackend.adapter.out.response.OrderDetailsResponse;
import com.example.shoppingbackend.domain.Customer;
import com.example.shoppingbackend.domain.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements GetAllOrdersUseCase, CreateOrderUseCase, GetOrderDetailUseCase {

    private final SaveOrderPort saveOrderPort;

    private final GetOrderDetailPort getOrderDetailPort;

    private final GetCustomerProfilePort getCustomerProfilePort;

    public OrderService(SaveOrderPort saveOrderPort,
                               GetOrderDetailPort getOrderDetailPort,
                               GetCustomerProfilePort getCustomerProfilePort) {
        this.saveOrderPort = saveOrderPort;
        this.getOrderDetailPort = getOrderDetailPort;
        this.getCustomerProfilePort = getCustomerProfilePort;
    }

    public void createOrder(CreateOrderCommand request) {
        saveOrderPort.saveOrder(request.getCustomerId(), request.getOrderItemRequestList());
    }

    public CustomerOrdersResponse findAllOrdersByCustomerId(Long id) {
        Customer customer = getCustomerProfilePort.getCustomerById(id);

        List<OrderDetailsResponse> orderDetailResponses = customer.getOrders().stream()
                .map(this::convertOrderToOrderDetailResponse).toList();
        return new CustomerOrdersResponse(orderDetailResponses);
    }

    public OrderDetailsResponse findOrderByOrderId(Long orderId) {
        Order order = getOrderDetailPort.getOrderById(orderId);
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
