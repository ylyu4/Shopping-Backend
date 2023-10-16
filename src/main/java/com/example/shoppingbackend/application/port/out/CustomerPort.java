package com.example.shoppingbackend.application.port.out;

import com.example.shoppingbackend.domain.Customer;

public interface CustomerPort {

    Customer getCustomerById(Long id);

}
