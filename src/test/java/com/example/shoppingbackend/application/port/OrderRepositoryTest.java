package com.example.shoppingbackend.application.port;

import com.example.shoppingbackend.application.port.out.OrderRepository;
import com.example.shoppingbackend.constant.ProductStatus;
import com.example.shoppingbackend.domain.Customer;
import com.example.shoppingbackend.domain.Order;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Rollback
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestEntityManager
public class OrderRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    OrderRepository orderRepository;

    @Test
    void should_save_order_successfully() {
        // given
        Customer customer = new Customer("customer");
        Product product = new Product("test1", 20, ProductStatus.VALID);
        Order order = new Order(customer);
        order.addOrderItem(product, 10);
        entityManager.persistAndFlush(customer);
        entityManager.persistAndFlush(product);
        orderRepository.save(order);

        // when
        List<Order> all = orderRepository.findAll();

        // then
        assertEquals(1, all.size());
        assertEquals(customer, all.get(0).getCustomer());
        assertEquals(product, all.get(0).getOrderItems().get(0).getProduct());
    }

    @Test
    void should_get_order_by_id_successfully() {
        // given
        Customer customer = new Customer("customer");
        Product product = new Product("test1", 20, ProductStatus.VALID);
        Order order = new Order(customer);
        order.addOrderItem(product, 10);
        entityManager.persistAndFlush(customer);
        entityManager.persistAndFlush(product);
        entityManager.persistAndFlush(order);

        // when
        List<Order> all = orderRepository.findAll();
        Long id = all.get(all.size() - 1).getId();
        Optional<Order> optionalOrder = orderRepository.findById(id);

        // then
        assertTrue(optionalOrder.isPresent());
        assertEquals(customer, optionalOrder.get().getCustomer());
        assertEquals(product, optionalOrder.get().getOrderItems().get(0).getProduct());
    }
}
