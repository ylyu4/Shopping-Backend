package com.example.shoppingbackend.application.service;

import com.example.shoppingbackend.application.port.out.GetCustomerProfilePort;
import com.example.shoppingbackend.application.port.out.GetOrderDetailPort;
import com.example.shoppingbackend.application.port.out.SaveOrderPort;
import com.example.shoppingbackend.domain.constant.ProductStatus;
import com.example.shoppingbackend.domain.Customer;
import com.example.shoppingbackend.domain.Order;
import com.example.shoppingbackend.domain.OrderItem;
import com.example.shoppingbackend.domain.Product;
import com.example.shoppingbackend.exception.CustomerNotFoundException;
import com.example.shoppingbackend.exception.OrderNotFoundException;
import com.example.shoppingbackend.adapter.in.request.CreateOrderRequest;
import com.example.shoppingbackend.adapter.in.response.CustomerOrdersResponse;
import com.example.shoppingbackend.adapter.in.response.OrderDetailsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderUseCaseTest {

    @InjectMocks
    OrderService orderService;

    @Mock
    SaveOrderPort saveOrderPort;

    @Mock
    GetOrderDetailPort getOrderDetailPort;

    @Mock
    GetCustomerProfilePort getCustomerProfilePort;

    CreateOrderRequest request;

    @BeforeEach
    void init() {
        CreateOrderRequest.OrderItemRequest orderItemRequest = new CreateOrderRequest.OrderItemRequest();
        orderItemRequest.setId(1L);
        orderItemRequest.setQuantity(2);

        request = new CreateOrderRequest();
        request.setCustomerId(1L);
        request.setOrderItemRequestList(List.of(orderItemRequest));
    }


    @Test
    void should_create_order_successfully() {
        // when
        orderService.createOrder(request);

        // then
        verify(saveOrderPort, times(1)).saveOrder(any(), any());
    }

    @Test
    void should_return_the_correct_order_response() {
        // given
        Order order = new Order();
        Product product = new Product(1L, "bike", 130, ProductStatus.VALID);
        OrderItem orderItem = new OrderItem(product, 3);
        order.setOrderItems(List.of(orderItem));
        Customer customer = new Customer(1L, "jack");
        customer.setOrders(List.of(order));

        when(getCustomerProfilePort.getCustomerById(2L)).thenReturn(customer);

        // when
        CustomerOrdersResponse response = orderService.findAllOrdersByCustomerId(2L);

        // then
        assertEquals(1, response.getOrders().size());
        assertEquals(390, response.getOrders().get(0).getTotalPrice());
    }

    @Test
    void should_throw_another_exception_when_customer_id_is_invalid() {
        // given
        when(getCustomerProfilePort.getCustomerById(1L)).thenThrow(new CustomerNotFoundException("not found"));
        // then
        assertThrows(CustomerNotFoundException.class, () -> orderService.findAllOrdersByCustomerId(1L));
    }

    @Test
    void should_return_the_correct_order_response_by_id() {
        // given
        Order order = new Order();
        Product product = new Product(1L, "bike", 130, ProductStatus.VALID);
        OrderItem orderItem = new OrderItem(product, 3);
        order.setOrderItems(List.of(orderItem));
        Customer customer = new Customer(1L, "jack");
        customer.setOrders(List.of(order));

        when(getOrderDetailPort.getOrderById(any())).thenReturn(order);

        // when
        OrderDetailsResponse response = orderService.findOrderByOrderId(2L);

        // then
        assertEquals(390, response.getTotalPrice());
    }

    @Test
    void should_throw_exception_when_order_id_is_invalid() {
        // given
        when(getOrderDetailPort.getOrderById(1L)).thenThrow(new OrderNotFoundException("not found"));
        // then
        assertThrows(OrderNotFoundException.class, () -> orderService.findOrderByOrderId(1L));
    }
}