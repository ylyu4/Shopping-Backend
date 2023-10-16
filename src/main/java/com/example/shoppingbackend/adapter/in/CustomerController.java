package com.example.shoppingbackend.adapter.in;

import com.example.shoppingbackend.application.port.in.CustomerUseCase;
import com.example.shoppingbackend.domain.Customer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerUseCase customerUseCase;

    public CustomerController(CustomerUseCase customerUseCase) {
        this.customerUseCase = customerUseCase;
    }

    @GetMapping("/profile/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerUseCase.getCustomer(id);
    }
}
