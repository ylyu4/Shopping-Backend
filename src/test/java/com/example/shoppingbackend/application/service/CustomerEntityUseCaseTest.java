package com.example.shoppingbackend.application.service;

import com.example.shoppingbackend.application.port.out.CustomerPort;
import com.example.shoppingbackend.domain.Customer;
import com.example.shoppingbackend.exception.CustomerNotFoundException;
import com.example.shoppingbackend.adapter.persistence.CustomerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerEntityUseCaseTest {

    @InjectMocks
    CustomerService customerService;

    @Mock
    CustomerPort customerPort;

    Customer customer;


    @BeforeEach
    void setUp() {
        customer = new Customer(1L, "customer1");
    }

    @Test
    void should_get_the_customer_info_by_id_if_customer_exists() {
        // given
        when(customerPort.getCustomerById(1L)).thenReturn(customer);

        // when
        Customer response = customerService.getCustomer(1L);

        // then
        assertEquals(customer, response);
    }

    @Test
    void should_throw_exception_when_can_not_found_customer_by_id() {
        // given
        when(customerPort.getCustomerById(1L)).thenThrow(CustomerNotFoundException.class);

        //then
        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomer(1L));
    }

}