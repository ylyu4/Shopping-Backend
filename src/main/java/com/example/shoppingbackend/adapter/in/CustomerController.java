package com.example.shoppingbackend.adapter.in;

import com.example.shoppingbackend.application.port.in.GetCustomerProfileUseCase;
import com.example.shoppingbackend.domain.Customer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final GetCustomerProfileUseCase getCustomerProfileUseCase;

    public CustomerController(GetCustomerProfileUseCase getCustomerProfileUseCase) {
        this.getCustomerProfileUseCase = getCustomerProfileUseCase;
    }

    @GetMapping("/profile/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return getCustomerProfileUseCase.getCustomerProfile(id);
    }
}
