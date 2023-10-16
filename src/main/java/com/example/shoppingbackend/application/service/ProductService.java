package com.example.shoppingbackend.application.service;


import com.example.shoppingbackend.application.port.in.ProductUseCase;
import com.example.shoppingbackend.application.port.out.ProductPort;
import com.example.shoppingbackend.domain.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements ProductUseCase {

    private final ProductPort productPort;

    public ProductService(ProductPort productPort) {
        this.productPort = productPort;
    }

    public List<Product> getProductList() {
        return productPort.getAllProduct();
    }

}
