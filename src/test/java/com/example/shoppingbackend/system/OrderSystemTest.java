package com.example.shoppingbackend.system;

import com.example.shoppingbackend.adapter.in.command.CreateOrderCommand;
import com.example.shoppingbackend.adapter.out.response.CustomerOrdersResponse;
import com.example.shoppingbackend.adapter.out.response.OrderDetailsResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderSystemTest {

    @LocalServerPort
    int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void should_create_order_successfully() {
        String url = "http://localhost:" + port + "/order";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        CreateOrderCommand.OrderItemRequest orderItemRequest = new CreateOrderCommand.OrderItemRequest();
        orderItemRequest.setId(1L);
        orderItemRequest.setQuantity(2);

        CreateOrderCommand createOrderCommand = new CreateOrderCommand();
        createOrderCommand.setCustomerId(1L);
        createOrderCommand.setOrderItemRequestList(List.of(orderItemRequest));

        HttpEntity<CreateOrderCommand> requestEntity = new HttpEntity<>(createOrderCommand, headers);

        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Object.class);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

    }

    @Test
    public void should_create_order_failed_because_customer_id_is_invalid() {
        String url = "http://localhost:" + port + "/order";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        CreateOrderCommand.OrderItemRequest orderItemRequest = new CreateOrderCommand.OrderItemRequest();
        orderItemRequest.setId(1L);
        orderItemRequest.setQuantity(2);

        CreateOrderCommand createOrderCommand = new CreateOrderCommand();
        createOrderCommand.setCustomerId(100L);
        createOrderCommand.setOrderItemRequestList(List.of(orderItemRequest));

        HttpEntity<CreateOrderCommand> requestEntity = new HttpEntity<>(createOrderCommand, headers);

        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Object.class);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void should_create_order_failed_because_product_id_is_invalid() {
        String url = "http://localhost:" + port + "/order";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        CreateOrderCommand.OrderItemRequest orderItemRequest = new CreateOrderCommand.OrderItemRequest();
        orderItemRequest.setId(100L);
        orderItemRequest.setQuantity(2);

        CreateOrderCommand createOrderCommand = new CreateOrderCommand();
        createOrderCommand.setCustomerId(100L);
        createOrderCommand.setOrderItemRequestList(List.of(orderItemRequest));

        HttpEntity<CreateOrderCommand> requestEntity = new HttpEntity<>(createOrderCommand, headers);

        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Object.class);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void get_customer_orders_response_by_customer_id() {
        String url = "http://localhost:" + port + "/order/1";

        CustomerOrdersResponse response = restTemplate.getForObject(url, CustomerOrdersResponse.class);

        Assertions.assertNotNull(response);
    }

    @Test
    public void throw_exception_when_customer_id_is_invalid() {
        String url = "http://localhost:" + port + "/order/100";

        ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void get_orders_detail_response_by_order_id() {
        String url = "http://localhost:" + port + "/order/detail/1";

        OrderDetailsResponse response = restTemplate.getForObject(url, OrderDetailsResponse.class);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(860, response.getTotalPrice());
    }

    @Test
    public void throw_exception_when_order_id_is_invalid() {
        String url = "http://localhost:" + port + "/order/detail/100";

        ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
