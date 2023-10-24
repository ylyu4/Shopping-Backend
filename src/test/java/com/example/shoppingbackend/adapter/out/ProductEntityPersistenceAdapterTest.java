package com.example.shoppingbackend.adapter.out;

import com.example.shoppingbackend.domain.constant.ProductStatus;
import com.example.shoppingbackend.adapter.persistence.ProductEntity;
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
class ProductEntityPersistenceAdapterTest {

    @InjectMocks
    ProductPersistenceAdapter productPersistenceAdapter;

    @Mock
    ProductRepository productRepository;


    @Test
    void should_get_the_product_list_correctly() {
        // given
        List<ProductEntity> list = List.of(new ProductEntity(1L, "product1", 10, 0.9, ProductStatus.VALID),
                new ProductEntity(2L, "product2", 20, 0.8, ProductStatus.VALID),
                new ProductEntity(3L, "product3", null, 0.6, ProductStatus.VALID),
                new ProductEntity(4L, "product4", 10, 0.7, ProductStatus.INVALID));

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
        ProductEntity product = new ProductEntity(1L, "product1", 10, 0.1, ProductStatus.VALID);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // when
        Product response = productPersistenceAdapter.getProductById(1L);

        // then
        assertEquals(product.getName(), response.getName());
    }

    @Test
    void should_throw_exception_when_can_not_found_customer_by_id() {
        // given
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        //then
        assertThrows(ProductNotFoundException.class, () -> productPersistenceAdapter.getProductById(1L));
    }
}