package com.example.shoppingbackend.application.service;

import com.example.shoppingbackend.adapter.out.CustomerPersistenceAdapter;
import com.example.shoppingbackend.application.port.in.CustomerUseCase;
import com.example.shoppingbackend.domain.Customer;

import org.springframework.stereotype.Service;

@Service
public class CustomerService implements CustomerUseCase {

    private final CustomerPersistenceAdapter customerPersistenceAdapter;

    public CustomerService(CustomerPersistenceAdapter customerPersistenceAdapter) {
        this.customerPersistenceAdapter = customerPersistenceAdapter;
    }

    public Customer getCustomer(Long id) {
        return customerPersistenceAdapter.getCustomerById(id);
    }
}
