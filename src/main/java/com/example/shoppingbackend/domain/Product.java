package com.example.shoppingbackend.domain;

import com.example.shoppingbackend.adapter.persistence.ProductEntity;


public class Product {

    private Long id;

    private String name;

    private Double originalPrice;

    private Double discountRate;

    private Double finalPrice;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public Double getDiscountRate() {
        return discountRate;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public Product(ProductEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.originalPrice = entity.getPrice().doubleValue();
        this.discountRate = entity.getDiscountRate();
        this.finalPrice = entity.getDiscountRate() * entity.getPrice();
    }

    public Product(Long id, String name, Double discountRate, Double originalPrice) {
        this.id = id;
        this.name = name;
        this.discountRate = discountRate;
        this.originalPrice = originalPrice;
        this.finalPrice = discountRate * originalPrice;
    }
}
