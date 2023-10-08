package com.example.shoppingbackend.service;

import com.example.shoppingbackend.constant.ProductStatus;
import com.example.shoppingbackend.exception.CustomerNotFoundException;
import com.example.shoppingbackend.exception.ProductNotFoundException;
import com.example.shoppingbackend.model.Customer;
import com.example.shoppingbackend.model.Order;
import com.example.shoppingbackend.model.OrderItem;
import com.example.shoppingbackend.model.Product;
import com.example.shoppingbackend.model.request.CreateOrderRequest;
import com.example.shoppingbackend.model.response.CustomerOrdersResponse;
import com.example.shoppingbackend.repository.CustomerRepository;
import com.example.shoppingbackend.repository.OrderRepository;
import com.example.shoppingbackend.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

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
    OrderRepository orderRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    CustomerRepository customerRepository;

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
        // given
        when(customerRepository.findById(1L)).thenReturn(Optional.of(new Customer()));
        when(productRepository.findById(1L)).thenReturn(Optional.of(new Product()));

        // when
        orderService.createOrder(request);

        // then
        verify(orderRepository, times(1)).save(any());
    }

    @Test
    void should_throw_exception_when_request_id_is_invalid() {
        // given
        when(customerRepository.findById(1L)).thenReturn(Optional.of(new Customer()));
        when(productRepository.findById(1L)).thenThrow(new ProductNotFoundException("Can not find product for this id: 1L"));

        // then
        assertThrows(ProductNotFoundException.class, () -> orderService.createOrder(request));
    }

    @Test
    void should_throw_exception_when_customer_id_id_invalid() {
        // given
        when(customerRepository.findById(1L)).thenThrow(new CustomerNotFoundException("not found"));
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

        when(customerRepository.findById(2L)).thenReturn(Optional.of(customer));

        // when
        CustomerOrdersResponse response = orderService.findAllOrderByCustomerId(2L);

        // then
        assertEquals(1, response.getOrders().size());
        assertEquals(390, response.getOrders().get(0).getTotalPrice());
    }

    @Test
    void should_throw_another_exception_when_customer_id_id_invalid() {
        // given
        when(customerRepository.findById(1L)).thenThrow(new CustomerNotFoundException("not found"));
        // then
        assertThrows(CustomerNotFoundException.class, () -> orderService.findAllOrderByCustomerId(1L));
    }


}