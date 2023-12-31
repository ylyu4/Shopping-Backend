package com.example.shoppingbackend.application.service;

import com.example.shoppingbackend.application.port.out.GetCustomerProfilePort;
import com.example.shoppingbackend.application.port.out.GetOrderDetailPort;
import com.example.shoppingbackend.application.port.out.GetProductDetailPort;
import com.example.shoppingbackend.application.port.out.SaveOrderPort;
import com.example.shoppingbackend.domain.Customer;
import com.example.shoppingbackend.domain.Order;
import com.example.shoppingbackend.domain.OrderItem;
import com.example.shoppingbackend.domain.Product;
import com.example.shoppingbackend.exception.CustomerNotFoundException;
import com.example.shoppingbackend.exception.OrderNotFoundException;
import com.example.shoppingbackend.adapter.in.request.CreateOrderRequest;
import com.example.shoppingbackend.adapter.in.response.CustomerOrdersResponse;
import com.example.shoppingbackend.adapter.in.response.OrderDetailsResponse;
import com.example.shoppingbackend.exception.ProductStockNotEnoughException;
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
import static org.mockito.Mockito.doThrow;
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
    GetProductDetailPort getProductDetailPort;

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
        Product product = new Product(1L, "bike", 0.8, 130.0);
        OrderItem orderItem = new OrderItem(product, 3);
        order.setOrderItems(List.of(orderItem));
        Customer customer = new Customer(1L, "jack");
        customer.setOrders(List.of(order));

        when(getCustomerProfilePort.getCustomerById(2L)).thenReturn(customer);

        // when
        CustomerOrdersResponse response = orderService.findAllOrdersByCustomerId(2L);

        // then
        assertEquals(1, response.getOrders().size());
        assertEquals(312.0, response.getOrders().get(0).getFinalTotalPrice());
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
        Product product = new Product(1L, "bike", 0.8, 130.0);
        OrderItem orderItem = new OrderItem(product, 3);
        order.setOrderItems(List.of(orderItem));
        Customer customer = new Customer(1L, "jack");
        customer.setOrders(List.of(order));

        when(getOrderDetailPort.getOrderById(any())).thenReturn(order);

        // when
        OrderDetailsResponse response = orderService.findOrderByOrderId(2L);

        // then
        assertEquals(312.0, response.getFinalTotalPrice());
        assertEquals(1, response.getOrderProducts().size());
        assertEquals(390.0, response.getOrderProducts().get(0).getOriginalPrice());
        assertEquals(78.0, response.getOrderProducts().get(0).getDiscountedPrice());
        assertEquals(312.0, response.getOrderProducts().get(0).getFinalPrice());
    }

    @Test
    void should_throw_exception_when_order_id_is_invalid() {
        // given
        when(getOrderDetailPort.getOrderById(1L)).thenThrow(new OrderNotFoundException("not found"));
        // then
        assertThrows(OrderNotFoundException.class, () -> orderService.findOrderByOrderId(1L));
    }

    @Test
    void should_throw_exception_when_product_quantity_is_not_enough() {
        // given
        doThrow(new ProductStockNotEnoughException("not enough")).when(saveOrderPort).saveOrder(any(), any());
        // then
        assertThrows(ProductStockNotEnoughException.class, () -> orderService.createOrder(request));
    }
}