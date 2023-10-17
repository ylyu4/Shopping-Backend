package com.example.shoppingbackend.adapter.in;

import com.example.shoppingbackend.adapter.in.command.CreateOrderCommand;
import com.example.shoppingbackend.adapter.in.response.CustomerOrdersResponse;
import com.example.shoppingbackend.adapter.in.response.OrderDetailsResponse;
import com.example.shoppingbackend.application.port.in.CreateOrderUseCase;
import com.example.shoppingbackend.application.port.in.GetAllOrdersUseCase;
import com.example.shoppingbackend.application.port.in.GetOrderDetailUseCase;
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

    private final GetAllOrdersUseCase getAllOrdersUseCase;

    private final GetOrderDetailUseCase getOrderDetailUseCase;

    private final CreateOrderUseCase createOrderUseCase;

    public OrderController(GetAllOrdersUseCase getAllOrdersUseCase, GetOrderDetailUseCase getOrderDetailUseCase, CreateOrderUseCase createOrderUseCase) {
        this.getAllOrdersUseCase = getAllOrdersUseCase;
        this.getOrderDetailUseCase = getOrderDetailUseCase;
        this.createOrderUseCase = createOrderUseCase;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestBody CreateOrderCommand createOrderCommand) {
        createOrderUseCase.createOrder(createOrderCommand);
    }

    @GetMapping("/{userId}")
    public CustomerOrdersResponse getAllOrdersByCustomerId(@PathVariable Long userId) {
        return getAllOrdersUseCase.findAllOrdersByCustomerId(userId);
    }

    @GetMapping("/detail/{orderId}")
    public OrderDetailsResponse getOrderDetailByOrderId(@PathVariable Long orderId) {
        return getOrderDetailUseCase.findOrderByOrderId(orderId);
    }
}
