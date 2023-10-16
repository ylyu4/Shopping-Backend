package com.example.shoppingbackend.adapter.out;

import com.example.shoppingbackend.adapter.in.command.CreateOrderCommand;
import com.example.shoppingbackend.adapter.persistence.CustomerEntity;
import com.example.shoppingbackend.adapter.persistence.OrderEntity;
import com.example.shoppingbackend.adapter.persistence.OrderItemEntity;
import com.example.shoppingbackend.adapter.persistence.ProductEntity;
import com.example.shoppingbackend.application.port.out.OrderPort;
import com.example.shoppingbackend.domain.Order;
import com.example.shoppingbackend.exception.CustomerNotFoundException;
import com.example.shoppingbackend.exception.OrderNotFoundException;
import com.example.shoppingbackend.exception.ProductNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderPersistenceAdapter implements OrderPort {

    private final OrderRepository orderRepository;

    private final CustomerRepository customerRepository;

    private final ProductRepository productRepository;

    public OrderPersistenceAdapter(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }


    public void saveOrder(Long id, List<CreateOrderCommand.OrderItemRequest> orderItemRequestList) {
        CustomerEntity customer = customerRepository.findById(id).orElseThrow(() ->
                new CustomerNotFoundException(String.format("Can not find customer by this id: %s", id)));
        List<OrderItemEntity> orderItems = orderItemRequestList.stream().map(it -> {
            ProductEntity product = productRepository.findById(it.getId()).orElseThrow(() ->
                    new ProductNotFoundException(String.format("Can not find product for this id: %s", id)));
            return new OrderItemEntity(product, it.getQuantity());
        }).toList();

        OrderEntity order = new OrderEntity(customer);
        orderItems.forEach(it -> order.addOrderItem(it.getProduct(), it.getQuantity()));
        orderRepository.save(order);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).map(Order::new).orElseThrow(() ->
                new OrderNotFoundException(String.format("Can not find order by this id: %s", id)));
    }
}
