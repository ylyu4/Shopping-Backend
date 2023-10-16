package com.example.shoppingbackend.application.service;

import com.example.shoppingbackend.application.port.out.CustomerPort;
import com.example.shoppingbackend.application.port.out.OrderPort;
import com.example.shoppingbackend.application.port.out.ProductPort;
import com.example.shoppingbackend.constant.ProductStatus;
import com.example.shoppingbackend.domain.Customer;
import com.example.shoppingbackend.domain.Order;
import com.example.shoppingbackend.domain.OrderItem;
import com.example.shoppingbackend.domain.Product;
import com.example.shoppingbackend.exception.CustomerNotFoundException;
import com.example.shoppingbackend.exception.OrderNotFoundException;
import com.example.shoppingbackend.exception.ProductNotFoundException;
import com.example.shoppingbackend.adapter.persistence.CustomerEntity;
import com.example.shoppingbackend.adapter.persistence.OrderEntity;
import com.example.shoppingbackend.adapter.persistence.OrderItemEntity;
import com.example.shoppingbackend.adapter.persistence.ProductEntity;
import com.example.shoppingbackend.adapter.in.command.CreateOrderCommand;
import com.example.shoppingbackend.adapter.out.response.CustomerOrdersResponse;
import com.example.shoppingbackend.adapter.out.response.OrderDetailsResponse;
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
class OrderEntityUseCaseTest {

    @InjectMocks
    OrderService orderService;

    @Mock
    OrderPort orderPort;

    @Mock
    ProductPort productPort;

    @Mock
    CustomerPort customerPort;

    CreateOrderCommand request;

    @BeforeEach
    void init() {
        CreateOrderCommand.OrderItemRequest orderItemRequest = new CreateOrderCommand.OrderItemRequest();
        orderItemRequest.setId(1L);
        orderItemRequest.setQuantity(2);

        request = new CreateOrderCommand();
        request.setCustomerId(1L);
        request.setOrderItemRequestList(List.of(orderItemRequest));
    }


    @Test
    void should_create_order_successfully() {
        // when
        orderService.createOrder(request);

        // then
        verify(orderPort, times(1)).saveOrder(any(), any());
    }

//    @Test
//    void should_throw_exception_when_request_id_is_invalid() {
//        // given
//        when(customerPort.getCustomerById(1L)).thenReturn(new Customer(1L, "jack"));
//        when(productPort.getProductById(1L)).thenThrow(new ProductNotFoundException("Can not find product for this id: 1L"));
//
//        // then
//        assertThrows(ProductNotFoundException.class, () -> orderService.createOrder(request));
//    }

//    @Test
//    void should_throw_exception_when_customer_id_id_invalid() {
//        // given
//        when(customerPort.getCustomerById(1L)).thenThrow(new CustomerNotFoundException("not found"));
//        // then
//        assertThrows(CustomerNotFoundException.class, () -> orderService.createOrder(request));
//    }

    @Test
    void should_return_the_correct_order_response() {
        // given
        Order order = new Order();
        Product product = new Product(1L, "bike", 130, ProductStatus.VALID);
        OrderItem orderItem = new OrderItem(product, 3);
        order.setOrderItems(List.of(orderItem));
        Customer customer = new Customer(1L, "jack");
        customer.setOrders(List.of(order));

        when(customerPort.getCustomerById(2L)).thenReturn(customer);

        // when
        CustomerOrdersResponse response = orderService.findAllOrderByCustomerId(2L);

        // then
        assertEquals(1, response.getOrders().size());
        assertEquals(390, response.getOrders().get(0).getTotalPrice());
    }

    @Test
    void should_throw_another_exception_when_customer_id_is_invalid() {
        // given
        when(customerPort.getCustomerById(1L)).thenThrow(new CustomerNotFoundException("not found"));
        // then
        assertThrows(CustomerNotFoundException.class, () -> orderService.findAllOrderByCustomerId(1L));
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

        when(orderPort.getOrderById(any())).thenReturn(order);

        // when
        OrderDetailsResponse response = orderService.findOrderByOrderId(2L);

        // then
        assertEquals(390, response.getTotalPrice());
    }

    @Test
    void should_throw_exception_when_order_id_is_invalid() {
        // given
        when(orderPort.getOrderById(1L)).thenThrow(new OrderNotFoundException("not found"));
        // then
        assertThrows(OrderNotFoundException.class, () -> orderService.findOrderByOrderId(1L));
    }
}