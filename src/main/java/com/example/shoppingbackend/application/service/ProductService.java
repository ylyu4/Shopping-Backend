package com.example.shoppingbackend.application.service;


import com.example.shoppingbackend.application.port.in.GetProductListUseCase;
import com.example.shoppingbackend.application.port.out.GetProductListPort;
import com.example.shoppingbackend.domain.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements GetProductListUseCase {

    private final GetProductListPort getProductListPort;

    public ProductService(GetProductListPort getProductListPort) {
        this.getProductListPort = getProductListPort;
    }

    public List<Product> getProductList() {
        return getProductListPort.getAllProduct();
    }

}
