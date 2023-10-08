package com.example.shoppingbackend.controller;


import com.example.shoppingbackend.model.request.CreateOrderRequest;
import com.example.shoppingbackend.model.response.CustomerOrdersResponse;
import com.example.shoppingbackend.model.response.OrderDetails;
import com.example.shoppingbackend.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        orderService.createOrder(createOrderRequest);
    }

    @GetMapping("/{userId}")
    public CustomerOrdersResponse getAllOrdersByCustomerId(@PathVariable Long userId) {
        return orderService.findAllOrderByCustomerId(userId);
    }

    @GetMapping("/detail/{orderId}")
    public OrderDetails getOrderDetailByOrderId(@PathVariable Long orderId) {
        return orderService.findOrderByOrderId(orderId);
    }
}
