package com.example.shoppingbackend.application.service;

import com.example.shoppingbackend.application.port.out.ProductPort;
import com.example.shoppingbackend.constant.ProductStatus;
import com.example.shoppingbackend.domain.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductEntityServiceTest {

    @InjectMocks
    ProductService productService;

    @Mock
    ProductPort productPort;


    @Test
    void should_get_the_product_list_correctly() {
        // given
        List<Product> list = List.of(new Product(1L, "product1", 10, ProductStatus.VALID),
                new Product(2L, "product2", 20, ProductStatus.VALID));

        when(productPort.getAllProduct()).thenReturn(list);

        // when
        List<Product> products = productService.getProductList();

        // then
        assertEquals(2, products.size());
        assertEquals(1L, products.get(0).getId());
        assertEquals(2L, products.get(1).getId());
    }


}