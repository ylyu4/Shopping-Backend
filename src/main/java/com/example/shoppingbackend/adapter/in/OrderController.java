package com.example.shoppingbackend.adapter.in;


import com.example.shoppingbackend.adapter.in.command.CreateOrderCommand;
import com.example.shoppingbackend.adapter.out.response.CustomerOrdersResponse;
import com.example.shoppingbackend.adapter.out.response.OrderDetailsResponse;
import com.example.shoppingbackend.application.port.in.OrderUseCase;
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

    private final OrderUseCase orderUseCase;

    public OrderController(OrderUseCase orderUseCase) {
        this.orderUseCase = orderUseCase;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestBody CreateOrderCommand createOrderCommand) {
        orderUseCase.createOrder(createOrderCommand);
    }

    @GetMapping("/{userId}")
    public CustomerOrdersResponse getAllOrdersByCustomerId(@PathVariable Long userId) {
        return orderUseCase.findAllOrderByCustomerId(userId);
    }

    @GetMapping("/detail/{orderId}")
    public OrderDetailsResponse getOrderDetailByOrderId(@PathVariable Long orderId) {
        return orderUseCase.findOrderByOrderId(orderId);
    }
}
