package com.example.shoppingbackend.application.service;

import com.example.shoppingbackend.application.port.in.GetCustomerProfileUseCase;
import com.example.shoppingbackend.application.port.out.GetCustomerProfilePort;

import com.example.shoppingbackend.domain.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements GetCustomerProfileUseCase {

    private final GetCustomerProfilePort getCustomerProfilePort;

    public CustomerService(GetCustomerProfilePort getCustomerProfilePort) {
        this.getCustomerProfilePort = getCustomerProfilePort;
    }

    public Customer getCustomerProfile(Long id) {
        return getCustomerProfilePort.getCustomerById(id);
    }
}
