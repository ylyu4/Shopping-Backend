package com.example.shoppingbackend.application.port;

import com.example.shoppingbackend.application.port.out.CustomerRepository;
import com.example.shoppingbackend.domain.Customer;
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

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Rollback
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestEntityManager
class CustomerRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    CustomerRepository customerRepository;


    @Test
    void should_get_customer_from_database_successfully() {
        // given
        Customer customer = new Customer("customer");
        entityManager.persistAndFlush(customer);

        // when
        List<Customer> all = customerRepository.findAll();
        Long id = all.get(all.size() - 1).getId();
        Optional<Customer> optionalCustomer = customerRepository.findById(id);

        // then
        assertTrue(optionalCustomer.isPresent());
        assertEquals("customer", optionalCustomer.get().getName());
    }

}