package com.example.shoppingbackend.application.port.out;

import com.example.shoppingbackend.domain.Product;

public interface GetProductDetailPort {

    Product getProductById(Long id);

}
