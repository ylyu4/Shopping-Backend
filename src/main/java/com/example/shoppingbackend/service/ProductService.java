package com.example.shoppingbackend.service;


import com.example.shoppingbackend.constant.ProductStatus;
import com.example.shoppingbackend.model.Product;
import com.example.shoppingbackend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public List<Product> getAllProduct() {
        return productRepository.findAll().stream().filter(it -> Objects.nonNull(it.getPrice()) &&
                it.getProductStatus() == ProductStatus.VALID).collect(Collectors.toList());
    }

}
