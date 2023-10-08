package com.example.shoppingbackend.service;

import com.example.shoppingbackend.constant.ProductStatus;
import com.example.shoppingbackend.model.Product;
import com.example.shoppingbackend.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;


    @Test
    void should_get_the_product_list_correctly() {
        // given
        List<Product> list = List.of(new Product(1L, "product1", 10, ProductStatus.VALID),
                new Product(2L, "product2", 20, ProductStatus.VALID),
                new Product(3L, "product3", null, ProductStatus.VALID),
                new Product(4L, "product4", 10, ProductStatus.INVALID));

        when(productRepository.findAll()).thenReturn(list);

        // when
        List<Product> products = productService.getAllProduct();

        // then
        assertEquals(2, products.size());
        assertEquals(1L, products.get(0).getId());
        assertEquals(2L, products.get(1).getId());
    }


}