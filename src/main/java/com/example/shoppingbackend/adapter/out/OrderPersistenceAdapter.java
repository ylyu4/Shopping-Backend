package com.example.shoppingbackend.adapter.out;

import com.example.shoppingbackend.application.port.out.OrderRepository;
import com.example.shoppingbackend.domain.Order;
import com.example.shoppingbackend.exception.OrderNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class OrderPersistenceAdapter {

    private final OrderRepository orderRepository;


    public OrderPersistenceAdapter(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() ->
                new OrderNotFoundException(String.format("Can not find order by this id: %s", id)));
    }
}
