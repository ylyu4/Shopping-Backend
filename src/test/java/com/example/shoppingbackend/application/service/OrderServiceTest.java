package com.example.shoppingbackend.application.service;

import com.example.shoppingbackend.adapter.out.CustomerPersistenceAdapter;
import com.example.shoppingbackend.adapter.out.OrderPersistenceAdapter;
import com.example.shoppingbackend.adapter.out.ProductPersistenceAdapter;
import com.example.shoppingbackend.application.service.OrderService;
import com.example.shoppingbackend.constant.ProductStatus;
import com.example.shoppingbackend.exception.CustomerNotFoundException;
import com.example.shoppingbackend.exception.OrderNotFoundException;
import com.example.shoppingbackend.exception.ProductNotFoundException;
import com.example.shoppingbackend.domain.Customer;
import com.example.shoppingbackend.domain.Order;
import com.example.shoppingbackend.domain.OrderItem;
import com.example.shoppingbackend.domain.Product;
import com.example.shoppingbackend.application.port.in.command.CreateOrderCommand;
import com.example.shoppingbackend.application.port.out.response.CustomerOrdersResponse;
import com.example.shoppingbackend.application.port.out.response.OrderDetailsResponse;
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
class OrderServiceTest {

    @InjectMocks
    OrderService orderService;

    @Mock
    OrderPersistenceAdapter orderPersistenceAdapter;

    @Mock
    ProductPersistenceAdapter productPersistenceAdapter;

    @Mock
    CustomerPersistenceAdapter customerPersistenceAdapter;

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
        // given
        when(customerPersistenceAdapter.getCustomerById(1L)).thenReturn(new Customer());
        when(productPersistenceAdapter.getProductById(1L)).thenReturn(new Product());

        // when
        orderService.createOrder(request);

        // then
        verify(orderPersistenceAdapter, times(1)).saveOrder(any());
    }

    @Test
    void should_throw_exception_when_request_id_is_invalid() {
        // given
        when(customerPersistenceAdapter.getCustomerById(1L)).thenReturn(new Customer());
        when(productPersistenceAdapter.getProductById(1L)).thenThrow(new ProductNotFoundException("Can not find product for this id: 1L"));

        // then
        assertThrows(ProductNotFoundException.class, () -> orderService.createOrder(request));
    }

    @Test
    void should_throw_exception_when_customer_id_id_invalid() {
        // given
        when(customerPersistenceAdapter.getCustomerById(1L)).thenThrow(new CustomerNotFoundException("not found"));
        // then
        assertThrows(CustomerNotFoundException.class, () -> orderService.createOrder(request));
    }

    @Test
    void should_return_the_correct_order_response() {
        // given
        Order order = new Order();
        Product product = new Product("bike", 130, ProductStatus.VALID);
        OrderItem orderItem = new OrderItem(product, 3);
        order.setOrderItems(List.of(orderItem));
        Customer customer = new Customer("Jane");
        customer.setOrders(List.of(order));

        when(customerPersistenceAdapter.getCustomerById(2L)).thenReturn(customer);

        // when
        CustomerOrdersResponse response = orderService.findAllOrderByCustomerId(2L);

        // then
        assertEquals(1, response.getOrders().size());
        assertEquals(390, response.getOrders().get(0).getTotalPrice());
    }

    @Test
    void should_throw_another_exception_when_customer_id_is_invalid() {
        // given
        when(customerPersistenceAdapter.getCustomerById(1L)).thenThrow(new CustomerNotFoundException("not found"));
        // then
        assertThrows(CustomerNotFoundException.class, () -> orderService.findAllOrderByCustomerId(1L));
    }

    @Test
    void should_return_the_correct_order_response_by_id() {
        // given
        Order order = new Order();
        Product product = new Product("bike", 130, ProductStatus.VALID);
        OrderItem orderItem = new OrderItem(product, 3);
        order.setOrderItems(List.of(orderItem));
        Customer customer = new Customer("Jane");
        customer.setOrders(List.of(order));

        when(orderPersistenceAdapter.getOrderById(any())).thenReturn(order);

        // when
        OrderDetailsResponse response = orderService.findOrderByOrderId(2L);

        // then
        assertEquals(390, response.getTotalPrice());
    }

    @Test
    void should_throw_exception_when_order_id_is_invalid() {
        // given
        when(orderPersistenceAdapter.getOrderById(1L)).thenThrow(new OrderNotFoundException("not found"));
        // then
        assertThrows(OrderNotFoundException.class, () -> orderService.findOrderByOrderId(1L));
    }
}