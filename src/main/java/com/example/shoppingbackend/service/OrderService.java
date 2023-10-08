package com.example.shoppingbackend.service;

import com.example.shoppingbackend.exception.ProductNotFoundException;
import com.example.shoppingbackend.model.Order;
import com.example.shoppingbackend.model.OrderItem;
import com.example.shoppingbackend.model.Product;
import com.example.shoppingbackend.model.request.CreateOrderRequest;
import com.example.shoppingbackend.repository.OrderRepository;
import com.example.shoppingbackend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }


    public void createOrder(CreateOrderRequest request) {
        List<OrderItem> orderItems = request.getOrderItemRequestList().stream().map(it -> {
            Product product = productRepository.findById(it.getId()).orElseThrow(() ->
                    new ProductNotFoundException(String.format("Can not find product for this id: %s", it.getId())));
            return new OrderItem(product, it.getQuantity());
        }).toList();
        Order order = new Order();
        order.create();
        orderItems.forEach(it -> order.addOrderItem(it.getProduct(), it.getQuantity()));
        orderRepository.save(order);
    }

}
