package com.example.shoppingbackend.adapter.out;

import com.example.shoppingbackend.domain.constant.ProductStatus;
import com.example.shoppingbackend.adapter.persistence.CustomerEntity;
import com.example.shoppingbackend.adapter.persistence.OrderEntity;
import com.example.shoppingbackend.adapter.persistence.ProductEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Rollback
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestEntityManager
public class OrderEntityRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    OrderRepository orderRepository;

    @Test
    void should_save_order_successfully() {
        // given
        Integer originalSize = orderRepository.findAll().size();
        CustomerEntity customer = new CustomerEntity("customer");
        ProductEntity product = new ProductEntity("test1", 20, ProductStatus.VALID);
        OrderEntity order = new OrderEntity(customer);
        order.addOrderItem(product, 10);
        entityManager.persistAndFlush(customer);
        entityManager.persistAndFlush(product);
        orderRepository.save(order);

        // when
        List<OrderEntity> all = orderRepository.findAll();

        // then
        Integer totalSize = 1 + originalSize;
        assertEquals(totalSize, all.size());
        assertEquals(customer, all.get(totalSize - 1).getCustomer());
        assertEquals(product, all.get(totalSize - 1).getOrderItems().get(0).getProduct());
    }

    @Test
    void should_get_order_by_id_successfully() {
        // given
        CustomerEntity customer = new CustomerEntity("customer");
        ProductEntity product = new ProductEntity("test1", 20, ProductStatus.VALID);
        OrderEntity order = new OrderEntity(customer);
        order.addOrderItem(product, 10);
        entityManager.persistAndFlush(customer);
        entityManager.persistAndFlush(product);
        entityManager.persistAndFlush(order);

        // when
        List<OrderEntity> all = orderRepository.findAll();
        Long id = all.get(all.size() - 1).getId();
        Optional<OrderEntity> optionalOrder = orderRepository.findById(id);

        // then
        assertTrue(optionalOrder.isPresent());
        assertEquals(customer, optionalOrder.get().getCustomer());
        assertEquals(product, optionalOrder.get().getOrderItems().get(0).getProduct());
    }
}
