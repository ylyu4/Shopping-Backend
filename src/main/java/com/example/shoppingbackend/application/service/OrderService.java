package com.example.shoppingbackend.application.service;

import com.example.shoppingbackend.adapter.out.CustomerPersistenceAdapter;
import com.example.shoppingbackend.adapter.out.OrderPersistenceAdapter;
import com.example.shoppingbackend.adapter.out.ProductPersistenceAdapter;
import com.example.shoppingbackend.application.port.in.OrderUseCase;
import com.example.shoppingbackend.domain.Customer;
import com.example.shoppingbackend.domain.Order;
import com.example.shoppingbackend.domain.OrderItem;
import com.example.shoppingbackend.domain.Product;
import com.example.shoppingbackend.application.port.in.command.CreateOrderCommand;
import com.example.shoppingbackend.application.port.out.response.CustomerOrdersResponse;
import com.example.shoppingbackend.application.port.out.response.OrderDetailsResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements OrderUseCase {

    private final OrderPersistenceAdapter orderPersistenceAdapter;

    private final CustomerPersistenceAdapter customerPersistenceAdapter;

    private final ProductPersistenceAdapter productPersistenceAdapter;

    public OrderService(OrderPersistenceAdapter orderPersistenceAdapter,
                        CustomerPersistenceAdapter customerPersistenceAdapter,
                        ProductPersistenceAdapter productPersistenceAdapter) {
        this.orderPersistenceAdapter = orderPersistenceAdapter;
        this.customerPersistenceAdapter = customerPersistenceAdapter;
        this.productPersistenceAdapter = productPersistenceAdapter;
    }

    public void createOrder(CreateOrderCommand request) {
        Customer customer = customerPersistenceAdapter.getCustomerById(request.getCustomerId());
        List<OrderItem> orderItems = request.getOrderItemRequestList().stream().map(it -> {
            Product product = productPersistenceAdapter.getProductById(it.getId());
            return new OrderItem(product, it.getQuantity());
        }).toList();

        Order order = new Order(customer);
        orderItems.forEach(it -> order.addOrderItem(it.getProduct(), it.getQuantity()));
        orderPersistenceAdapter.saveOrder(order);
    }

    public CustomerOrdersResponse findAllOrderByCustomerId(Long id) {
        Customer customer = customerPersistenceAdapter.getCustomerById(id);

        List<OrderDetailsResponse> orderDetailResponses = customer.getOrders().stream()
                .map(this::convertOrderToOrderDetailResponse).toList();
        return new CustomerOrdersResponse(orderDetailResponses);
    }

    public OrderDetailsResponse findOrderByOrderId(Long orderId) {
        Order order = orderPersistenceAdapter.getOrderById(orderId);
        return convertOrderToOrderDetailResponse(order);
    }

    private OrderDetailsResponse convertOrderToOrderDetailResponse(Order order) {
        List<OrderDetailsResponse.OrderProductDetails> orderProductDetails = order.getOrderItems().stream()
                .map(orderItem -> new OrderDetailsResponse.OrderProductDetails(orderItem.getProduct().getName(),
                        orderItem.getQuantity(), orderItem.getProduct().getPrice())).toList();
        Integer totalPrice = orderProductDetails.stream()
                .mapToInt(orderItem -> orderItem.getPrice() * orderItem.getQuantity()).sum();
        return new OrderDetailsResponse(orderProductDetails, totalPrice, order.getOrderStatus());
    }
}
