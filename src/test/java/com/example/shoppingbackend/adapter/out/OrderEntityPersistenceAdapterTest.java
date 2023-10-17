package com.example.shoppingbackend.adapter.out;

import com.example.shoppingbackend.adapter.in.command.CreateOrderCommand;
import com.example.shoppingbackend.adapter.persistence.CustomerEntity;
import com.example.shoppingbackend.adapter.persistence.OrderEntity;
import com.example.shoppingbackend.domain.Order;
import com.example.shoppingbackend.exception.CustomerNotFoundException;
import com.example.shoppingbackend.exception.OrderNotFoundException;
import com.example.shoppingbackend.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderEntityPersistenceAdapterTest {

    @InjectMocks
    OrderPersistenceAdapter orderPersistenceAdapter;

    @Mock
    OrderRepository orderRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    CustomerRepository customerRepository;

    OrderEntity order;


    @BeforeEach
    void setUp() {
        order = new OrderEntity();
    }

        @Test
    void should_throw_exception_when_request_id_is_invalid() {
        // given
        when(customerRepository.findById(1L)).thenReturn(Optional.of(new CustomerEntity(1L, "jack")));
        when(productRepository.findById(1L)).thenThrow(new ProductNotFoundException("Can not find product for this id: 1L"));
        CreateOrderCommand.OrderItemRequest orderItemRequest = new CreateOrderCommand.OrderItemRequest();
        orderItemRequest.setId(1L);
        orderItemRequest.setQuantity(4);

        // then
        assertThrows(ProductNotFoundException.class, () -> orderPersistenceAdapter.saveOrder(1L, List.of(orderItemRequest)));
    }

    @Test
    void should_throw_exception_when_customer_id_id_invalid() {
        // given
        when(customerRepository.findById(1L)).thenThrow(new CustomerNotFoundException("not found"));
        // then
        assertThrows(CustomerNotFoundException.class, () -> orderPersistenceAdapter.saveOrder(1L, any()));
    }

    @Test
    void should_get_order_by_id_successfully() {
        //given
        when(orderRepository.findById(any())).thenReturn(Optional.of(order));

        //when
        Order resp = orderPersistenceAdapter.getOrderById(1L);

        //then
        assertEquals(order.getId(), resp.getId());
    }

    @Test
    void should_throw_exception_when_order_is_not_found() {
        //given
        when(orderRepository.findById(1L)).thenThrow(OrderNotFoundException.class);

        //then
        assertThrows(OrderNotFoundException.class, () -> orderPersistenceAdapter.getOrderById(1L));
    }

}