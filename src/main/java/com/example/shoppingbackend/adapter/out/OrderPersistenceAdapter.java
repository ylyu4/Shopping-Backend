package com.example.shoppingbackend.adapter.out;

import com.example.shoppingbackend.adapter.in.request.CreateOrderRequest;
import com.example.shoppingbackend.adapter.persistence.CustomerEntity;
import com.example.shoppingbackend.adapter.persistence.OrderEntity;
import com.example.shoppingbackend.adapter.persistence.OrderItemEntity;
import com.example.shoppingbackend.adapter.persistence.ProductEntity;
import com.example.shoppingbackend.application.port.out.GetOrderDetailPort;
import com.example.shoppingbackend.application.port.out.SaveOrderPort;
import com.example.shoppingbackend.domain.Order;
import com.example.shoppingbackend.exception.CustomerNotFoundException;
import com.example.shoppingbackend.exception.OrderNotFoundException;
import com.example.shoppingbackend.exception.ProductNotFoundException;
import com.example.shoppingbackend.exception.ProductStockNotEnoughException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderPersistenceAdapter implements SaveOrderPort, GetOrderDetailPort {

    private final OrderRepository orderRepository;

    private final CustomerRepository customerRepository;

    private final ProductRepository productRepository;

    public OrderPersistenceAdapter(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }


    public void saveOrder(Long id, List<CreateOrderRequest.OrderItemRequest> orderItemRequestList) {
        CustomerEntity customer = customerRepository.findById(id).orElseThrow(() ->
                new CustomerNotFoundException(String.format("Can not find customer by this id: %s", id)));
        List<OrderItemEntity> orderItems = orderItemRequestList.stream().map(it -> {
            ProductEntity product = productRepository.findById(it.getId()).orElseThrow(() ->
                    new ProductNotFoundException(String.format("Can not find product for this id: %s", id)));
            if (product.getStock() <= 0 || product.getStock() < it.getQuantity()) {
                throw new ProductStockNotEnoughException(String.format("The product does not have enough stock. " +
                        "Product id: %s", product.getId()));
            }
            return new OrderItemEntity(product, it.getQuantity());
        }).toList();

        OrderEntity order = new OrderEntity(customer);
        orderItems.forEach(it -> {
            ProductEntity product = it.getProduct();
            order.addOrderItem(product, it.getQuantity());
            product.updateStock(it.getQuantity());
            productRepository.save(product);
        });
        orderRepository.save(order);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).map(Order::new).orElseThrow(() ->
                new OrderNotFoundException(String.format("Can not find order by this id: %s", id)));
    }
}
