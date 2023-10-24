package com.example.shoppingbackend.adapter.persistence;

import com.example.shoppingbackend.domain.constant.ProductStatus;
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
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer price;

    @Column(name = "discount_rate")
    private Double discountRate;

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

    public Double getDiscountRate() {
        return discountRate;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public ProductEntity(){

    }

    public ProductEntity(String name, Integer price, Double discountRate, ProductStatus productStatus) {
        this.name = name;
        this.price = price;
        this.discountRate = discountRate;
        this.productStatus = productStatus;
    }

    public ProductEntity(Long id, String name, Integer price, Double discountRate, ProductStatus productStatus) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discountRate = discountRate;
        this.productStatus = productStatus;
    }
}
