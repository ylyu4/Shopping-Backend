package com.example.shoppingbackend.application.service;


import com.example.shoppingbackend.adapter.out.ProductPersistenceAdapter;
import com.example.shoppingbackend.application.port.in.ProductUseCase;
import com.example.shoppingbackend.domain.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements ProductUseCase {

    private final ProductPersistenceAdapter productPersistenceAdapter;

    public ProductService(ProductPersistenceAdapter productPersistenceAdapter) {
        this.productPersistenceAdapter = productPersistenceAdapter;
    }

    public List<Product> getProductList() {
        return productPersistenceAdapter.getAllProduct();
    }

}
