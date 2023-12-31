package com.example.shoppingbackend.adapter.in;

import com.example.shoppingbackend.application.port.in.CreateOrderUseCase;
import com.example.shoppingbackend.application.port.in.GetAllOrdersUseCase;
import com.example.shoppingbackend.application.port.in.GetOrderDetailUseCase;
import com.example.shoppingbackend.domain.Product;
import com.example.shoppingbackend.domain.constant.OrderStatus;
import com.example.shoppingbackend.exception.CustomerNotFoundException;
import com.example.shoppingbackend.exception.OrderNotFoundException;
import com.example.shoppingbackend.exception.ProductNotFoundException;
import com.example.shoppingbackend.adapter.in.request.CreateOrderRequest;
import com.example.shoppingbackend.adapter.in.response.CustomerOrdersResponse;
import com.example.shoppingbackend.adapter.in.response.OrderDetailsResponse;
import com.example.shoppingbackend.exception.ProductStockNotEnoughException;
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
public class OrderEntityControllerTest {

    @MockBean
    private GetAllOrdersUseCase getAllOrdersUseCase;

    @MockBean
    private CreateOrderUseCase createOrderUseCase;

    @MockBean
    private GetOrderDetailUseCase getOrderDetailUseCase;

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
        doNothing().when(createOrderUseCase).createOrder(createOrderRequest);

        mockMvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.write(createOrderRequest).getJson()))
                        .andExpect(status().isCreated());
    }

    @Test
    void should_throw_product_stock_not_enough_exception() throws Exception {
        doThrow(new ProductStockNotEnoughException("not enough")).when(createOrderUseCase).createOrder(any());

        mockMvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.write(createOrderRequest).getJson()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_throw_product_not_found_exception() throws Exception {
        doThrow(new ProductNotFoundException("not found")).when(createOrderUseCase).createOrder(any());

        mockMvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.write(createOrderRequest).getJson()))
                        .andExpect(status().isNotFound());
    }

    @Test
    void should_return_the_correct_order_response() throws Exception {
        Product product = new Product(1L, "name", 0.8, 10.0);
        OrderDetailsResponse.OrderProductDetails orderProductDetails = new OrderDetailsResponse.OrderProductDetails(product, 3);
        OrderDetailsResponse orderDetailsResponse = new OrderDetailsResponse(List.of(orderProductDetails), OrderStatus.CREATED);
        CustomerOrdersResponse response = new CustomerOrdersResponse(List.of(orderDetailsResponse));

        doReturn(response).when(getAllOrdersUseCase).findAllOrdersByCustomerId(1L);
        mockMvc.perform(get("/order/{userId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orders.[0].finalTotalPrice", is(24.0)))
                .andExpect(jsonPath("$.orders.[0].orderStatus", is("CREATED")));
    }

    @Test
    void should_throw_customer_not_found_exception() throws Exception {
        doThrow(new CustomerNotFoundException("not found")).when(getAllOrdersUseCase).findAllOrdersByCustomerId(1L);

        mockMvc.perform(get("/order/{userId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_return_the_correct_order_response_by_order_id() throws Exception {
        Product product = new Product(1L, "name", 0.8, 10.0);
        OrderDetailsResponse.OrderProductDetails orderProductDetails = new OrderDetailsResponse.OrderProductDetails(product, 3);
        OrderDetailsResponse orderDetailsResponse = new OrderDetailsResponse(List.of(orderProductDetails), OrderStatus.CREATED);

        doReturn(orderDetailsResponse).when(getOrderDetailUseCase).findOrderByOrderId(1L);
        mockMvc.perform(get("/order/detail/{orderId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.finalTotalPrice", is(24.0)))
                .andExpect(jsonPath("$.orderStatus", is("CREATED")));
    }

    @Test
    void should_throw_order_not_found_exception() throws Exception {
        doThrow(new OrderNotFoundException("not found")).when(getOrderDetailUseCase).findOrderByOrderId(1L);

        mockMvc.perform(get("/order/detail/{orderId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}