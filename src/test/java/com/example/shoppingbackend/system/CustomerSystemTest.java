package com.example.shoppingbackend.system;

import com.example.shoppingbackend.domain.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerSystemTest {

    @LocalServerPort
    int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void find_customer_profile_by_id() {
        String url = "http://localhost:" + port + "/customer/profile/1";

        Customer response = restTemplate.getForObject(url, Customer.class);

        Assertions.assertNotNull(response);
        Assertions.assertEquals("Jack", response.getName());
    }

    @Test
    public void throw_exception_when_id_is_invalid() {
        String url = "http://localhost:" + port + "/customer/profile/100";

        ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
