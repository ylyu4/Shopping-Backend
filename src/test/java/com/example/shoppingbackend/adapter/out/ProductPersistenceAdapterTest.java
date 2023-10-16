package com.example.shoppingbackend.adapter.out;

import com.example.shoppingbackend.application.port.out.ProductRepository;
import com.example.shoppingbackend.constant.ProductStatus;
import com.example.shoppingbackend.domain.Product;
import com.example.shoppingbackend.exception.ProductNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProductPersistenceAdapterTest {

    @InjectMocks
    ProductPersistenceAdapter productPersistenceAdapter;

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
        List<Product> products = productPersistenceAdapter.getAllProduct();

        // then
        assertEquals(2, products.size());
        assertEquals(1L, products.get(0).getId());
        assertEquals(2L, products.get(1).getId());
    }

    @Test
    void should_get_the_product_by_id_if_product_exists() {
        // given
        Product product = new Product(1L, "product1", 10, ProductStatus.VALID);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // when
        Product response = productPersistenceAdapter.getProductById(1L);

        // then
        assertEquals(product, response);
    }

    @Test
    void should_throw_exception_when_can_not_found_customer_by_id() {
        // given
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        //then
        assertThrows(ProductNotFoundException.class, () -> productPersistenceAdapter.getProductById(1L));
    }
}