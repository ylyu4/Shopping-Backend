package com.example.shoppingbackend.adapter.in;

import com.example.shoppingbackend.domain.Customer;
import com.example.shoppingbackend.application.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/profile/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.getCustomer(id);
    }
}
