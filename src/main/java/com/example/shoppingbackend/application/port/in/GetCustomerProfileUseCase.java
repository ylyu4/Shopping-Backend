package com.example.shoppingbackend.application.port.in;

import com.example.shoppingbackend.domain.Customer;

public interface GetCustomerProfileUseCase {

    Customer getCustomerProfile(Long id);

}
