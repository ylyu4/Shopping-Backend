package com.example.shoppingbackend.application.port.out;

import com.example.shoppingbackend.domain.Product;

import java.util.List;

public interface ProductPort {

    List<Product> getAllProduct();

    Product getProductById(Long id);
}
