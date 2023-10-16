package com.example.shoppingbackend.application.port;

import com.example.shoppingbackend.application.port.out.ProductRepository;
import com.example.shoppingbackend.constant.ProductStatus;
import com.example.shoppingbackend.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Rollback
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestEntityManager
public class ProductRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    ProductRepository productRepository;

    @Test
    void should_find_all_products_from_database_successfully() {
        // given
        Product product = new Product("test1", 20, ProductStatus.VALID);
        entityManager.persistAndFlush(product);

        // when
        List<Product> all = productRepository.findAll();

        // then
        assertEquals(5, all.size());
        assertEquals("test1", all.get(all.size() - 1).getName());
    }

}
