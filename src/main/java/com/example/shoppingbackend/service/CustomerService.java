package com.example.shoppingbackend.service;

import com.example.shoppingbackend.exception.CustomerNotFoundException;
import com.example.shoppingbackend.model.Customer;
import com.example.shoppingbackend.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() ->
                new CustomerNotFoundException(String.format("Can not find customer by this id: %s", id)));
    }
}
