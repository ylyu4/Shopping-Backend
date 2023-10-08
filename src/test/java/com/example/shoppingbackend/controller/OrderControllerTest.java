package com.example.shoppingbackend.controller;

import com.example.shoppingbackend.constant.OrderStatus;
import com.example.shoppingbackend.exception.CustomerNotFoundException;
import com.example.shoppingbackend.exception.ProductNotFoundException;
import com.example.shoppingbackend.model.request.CreateOrderRequest;
import com.example.shoppingbackend.model.response.CustomerOrdersResponse;
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

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
        createOrderRequest.setCustomerId(1L);
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

    @Test
    void should_return_the_correct_order_response() throws Exception {

        CustomerOrdersResponse.OrderProductDetails orderProductDetails = new CustomerOrdersResponse.OrderProductDetails("bike", 3, 35);
        CustomerOrdersResponse.OrderDetails orderDetails = new CustomerOrdersResponse.OrderDetails(List.of(orderProductDetails), 105, OrderStatus.CREATED);
        CustomerOrdersResponse response = new CustomerOrdersResponse(List.of(orderDetails));

        doReturn(response).when(orderService).findAllOrderByCustomerId(1L);
        mockMvc.perform(get("/order/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orders.[0].totalPrice", is(105)))
                .andExpect(jsonPath("$.orders.[0].orderStatus", is("CREATED")));
    }

    @Test
    void should_throw_customer_not_found_exception() throws Exception {

        doThrow(new CustomerNotFoundException("not found")).when(orderService).findAllOrderByCustomerId(1L);

        mockMvc.perform(get("/order/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}