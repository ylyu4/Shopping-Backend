package com.example.shoppingbackend.exception;

public class ProductStockNotEnoughException extends RuntimeException {

    public ProductStockNotEnoughException(String message) {
        super(message);
    }

}