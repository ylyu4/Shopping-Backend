package com.example.shoppingbackend.service;

import com.example.shoppingbackend.exception.CustomerNotFoundException;
import com.example.shoppingbackend.exception.OrderNotFoundException;
import com.example.shoppingbackend.exception.ProductNotFoundException;
import com.example.shoppingbackend.model.Customer;
import com.example.shoppingbackend.model.Order;
import com.example.shoppingbackend.model.OrderItem;
import com.example.shoppingbackend.model.Product;
import com.example.shoppingbackend.model.request.CreateOrderRequest;
import com.example.shoppingbackend.model.response.CustomerOrdersResponse;
import com.example.shoppingbackend.model.response.OrderDetails;
import com.example.shoppingbackend.repository.CustomerRepository;
import com.example.shoppingbackend.repository.OrderRepository;
import com.example.shoppingbackend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository,
                        CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }


    public void createOrder(CreateOrderRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow(() ->
                new CustomerNotFoundException(String.format("Can not find customer by this id: %s",
                        request.getCustomerId())));
        List<OrderItem> orderItems = request.getOrderItemRequestList().stream().map(it -> {
            Product product = productRepository.findById(it.getId()).orElseThrow(() ->
                    new ProductNotFoundException(String.format("Can not find product for this id: %s", it.getId())));
            return new OrderItem(product, it.getQuantity());
        }).toList();
        Order order = new Order(customer);
        orderItems.forEach(it -> order.addOrderItem(it.getProduct(), it.getQuantity()));
        orderRepository.save(order);
    }

    public CustomerOrdersResponse findAllOrderByCustomerId(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() ->
                new CustomerNotFoundException(String.format("Can not find customer by this id: %s", id)));

        List<OrderDetails> orderDetails = customer.getOrders().stream()
                .map(this::convertOrderToOrderDetailResponse).toList();
        return new CustomerOrdersResponse(orderDetails);
    }

    public OrderDetails findOrderByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new OrderNotFoundException(String.format("Can not find order by this id: %s", orderId)));
        return convertOrderToOrderDetailResponse(order);
    }

    private OrderDetails convertOrderToOrderDetailResponse(Order order) {
        List<OrderDetails.OrderProductDetails> orderProductDetails = order.getOrderItems().stream()
                .map(orderItem -> new OrderDetails.OrderProductDetails(orderItem.getProduct().getName(),
                        orderItem.getQuantity(), orderItem.getProduct().getPrice())).toList();
        Integer totalPrice = orderProductDetails.stream()
                .mapToInt(orderItem -> orderItem.getPrice() * orderItem.getQuantity()).sum();
        return new OrderDetails(orderProductDetails, totalPrice, order.getOrderStatus());
    }
}
