package com.example.shoppingbackend.adapter.out;

import com.example.shoppingbackend.application.port.out.OrderRepository;
import com.example.shoppingbackend.domain.Order;
import com.example.shoppingbackend.exception.OrderNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderPersistenceAdapterTest {

    @InjectMocks
    OrderPersistenceAdapter orderPersistenceAdapter;

    @Mock
    OrderRepository orderRepository;

    Order order;


    @BeforeEach
    void setUp() {
        order = new Order();
    }

    @Test
    void should_get_order_by_id_successfully() {
        //given
        when(orderRepository.findById(any())).thenReturn(Optional.of(order));

        //when
        Order resp = orderPersistenceAdapter.getOrderById(1L);

        //then
        assertEquals(order, resp);
    }

    @Test
    void should_throw_exception_when_order_is_not_found() {
        //given
        when(orderRepository.findById(1L)).thenThrow(OrderNotFoundException.class);

        //then
        assertThrows(OrderNotFoundException.class, () -> orderPersistenceAdapter.getOrderById(1L));
    }

}