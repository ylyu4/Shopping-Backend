package com.example.shoppingbackend.domain;

import com.example.shoppingbackend.adapter.persistence.ProductEntity;
import com.example.shoppingbackend.constant.ProductStatus;


public class Product {

    private Long id;

    private String name;

    private Integer price;

    private ProductStatus productStatus;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public Product(ProductEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.price = entity.getPrice();
        this.productStatus = entity.getProductStatus();
    }

    public Product(Long id, String name, Integer price, ProductStatus productStatus) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.productStatus = productStatus;
    }
}
