package com.example.shoppingbackend.application.port.in;

import com.example.shoppingbackend.domain.Product;

import java.util.List;

public interface GetProductListUseCase {

    List<Product> getProductList();
}
