package com.example.shoppingbackend.controller;

import com.example.shoppingbackend.exception.ProductNotFoundException;
import com.example.shoppingbackend.model.request.CreateOrderRequest;
import com.example.shoppingbackend.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureJsonTesters
public class OrderControllerTest {

    @MockBean
    private OrderService orderService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<CreateOrderRequest> request;

    CreateOrderRequest createOrderRequest;

    @BeforeEach
    void init() {
        CreateOrderRequest.OrderItemRequest orderItemRequest = new CreateOrderRequest.OrderItemRequest();
        orderItemRequest.setId(1L);
        orderItemRequest.setQuantity(2);

        createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setOrderItemRequestList(List.of(orderItemRequest));
    }

    @Test
    void should_create_order_successfully() throws Exception {

        doNothing().when(orderService).createOrder(createOrderRequest);

        mockMvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.write(createOrderRequest).getJson()))
                        .andExpect(status().isCreated());
    }

    @Test
    void should_throw_product_not_found_exception() throws Exception {

        doThrow(new ProductNotFoundException("not found")).when(orderService).createOrder(any());

        mockMvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.write(createOrderRequest).getJson()))
                        .andExpect(status().isNotFound());
    }
}