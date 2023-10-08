package com.example.shoppingbackend.model;

import com.example.shoppingbackend.constant.ProductStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer price;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_status")
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

    public Product(){

    }

    public Product(String name, Integer price, ProductStatus productStatus) {
        this.name = name;
        this.price = price;
        this.productStatus = productStatus;
    }

    public Product(Long id, String name, Integer price, ProductStatus productStatus) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.productStatus = productStatus;
    }
}
