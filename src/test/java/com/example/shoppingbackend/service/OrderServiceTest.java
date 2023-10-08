package com.example.shoppingbackend.service;

import com.example.shoppingbackend.exception.ProductNotFoundException;
import com.example.shoppingbackend.model.Product;
import com.example.shoppingbackend.model.request.CreateOrderRequest;
import com.example.shoppingbackend.repository.OrderRepository;
import com.example.shoppingbackend.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

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


    @Test
    void should_create_order_successfully() {
        // given
        CreateOrderRequest.OrderItemRequest orderItemRequest = new CreateOrderRequest.OrderItemRequest();
        orderItemRequest.setId(1L);
        orderItemRequest.setQuantity(2);

        CreateOrderRequest request = new CreateOrderRequest();
        request.setOrderItemRequestList(List.of(orderItemRequest));

        when(productRepository.findById(any())).thenReturn(Optional.of(new Product()));

        // when
        orderService.createOrder(request);

        // then
        verify(orderRepository, times(1)).save(any());
    }

    @Test
    void should_throw_exception_when_request_id_id_invalid() {
        // given
        CreateOrderRequest.OrderItemRequest orderItemRequest = new CreateOrderRequest.OrderItemRequest();
        orderItemRequest.setId(1L);
        orderItemRequest.setQuantity(2);

        CreateOrderRequest request = new CreateOrderRequest();
        request.setOrderItemRequestList(List.of(orderItemRequest));

        when(productRepository.findById(1L)).thenThrow(new ProductNotFoundException("Can not find product for this id: 1L"));

        // then
        assertThrows(ProductNotFoundException.class, () -> orderService.createOrder(request));
    }


}