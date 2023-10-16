package com.example.shoppingbackend.application.service;

import com.example.shoppingbackend.application.port.in.CustomerUseCase;
import com.example.shoppingbackend.application.port.out.CustomerPort;

import com.example.shoppingbackend.domain.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements CustomerUseCase {

    private final CustomerPort customerPort;

    public CustomerService(CustomerPort customerPort) {
        this.customerPort = customerPort;
    }

    public Customer getCustomer(Long id) {
        return customerPort.getCustomerById(id);
    }
}
