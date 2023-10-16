package com.example.shoppingbackend.system;

import com.example.shoppingbackend.domain.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductSystemTest {

    @LocalServerPort
    int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testFindAllProducts() {
        String url = "http://localhost:" + port + "/product/list";

        ParameterizedTypeReference<List<Product>> responseType = new ParameterizedTypeReference<List<Product>>() {};

        ResponseEntity<List<Product>> resp = restTemplate.exchange(url, HttpMethod.GET, null, responseType);

        List<Product> response = resp.getBody();
        Assertions.assertEquals(2, response.size());
        Assertions.assertEquals("book", response.get(0).getName());
        Assertions.assertEquals("sofa", response.get(1).getName());
    }
}