package com.example.shoppingbackend.service;

import com.example.shoppingbackend.exception.CustomerNotFoundException;
import com.example.shoppingbackend.model.Customer;
import com.example.shoppingbackend.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    Customer customer;


    @BeforeEach
    void setUp() {
        customer = new Customer(1L, "customer1");
    }

    @Test
    void should_get_the_customer_info_by_id_if_customer_exists() {
        // given
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        // when
        Customer response = customerService.getCustomerById(1L);

        // then
        assertEquals(customer, response);
    }

    @Test
    void should_throw_exception_when_can_not_found_customer_by_id() {
        // given
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        //then
        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerById(1L));
    }

}