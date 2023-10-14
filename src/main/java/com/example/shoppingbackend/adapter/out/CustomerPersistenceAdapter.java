package com.example.shoppingbackend.adapter.out;

import com.example.shoppingbackend.application.port.out.CustomerRepository;
import com.example.shoppingbackend.domain.Customer;
import com.example.shoppingbackend.exception.CustomerNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomerPersistenceAdapter {

    private final CustomerRepository customerRepository;


    public CustomerPersistenceAdapter(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() ->
                new CustomerNotFoundException(String.format("Can not find customer by this id: %s", id)));
    }
}