package com.example.shoppingbackend.adapter.in.response;

import com.example.shoppingbackend.domain.Product;
import com.example.shoppingbackend.domain.constant.OrderStatus;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class OrderDetailsResponse {

    private List<OrderProductDetails> orderProducts;

    private Double finalTotalPrice;

    private OrderStatus orderStatus;

    public List<OrderProductDetails> getOrderProducts() {
        return orderProducts;
    }

    public Double getFinalTotalPrice() {
        return finalTotalPrice;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public OrderDetailsResponse(List<OrderProductDetails> orderProducts, OrderStatus orderStatus) {
        this.orderProducts = orderProducts;
        this.finalTotalPrice = orderProducts.stream().mapToDouble(OrderProductDetails::getFinalPrice).sum();
        this.orderStatus = orderStatus;
    }

    public OrderDetailsResponse() {
    }

    public static class OrderProductDetails {

        private String name;

        private Integer quantity;

        private Double itemPrice;

        private Double originalPrice;

        private Double discountedPrice;

        private Double finalPrice;

        public String getName() {
            return name;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public Double getItemPrice() {
            return itemPrice;
        }

        public Double getOriginalPrice() {
            return originalPrice;
        }

        public Double getDiscountedPrice() {
            return discountedPrice;
        }

        public Double getFinalPrice() {
            return finalPrice;
        }

        public OrderProductDetails(Product product, int quantity) {
            this.name = product.getName();
            this.quantity = quantity;
            this.itemPrice = product.getOriginalPrice();
            this.originalPrice = BigDecimal.valueOf(product.getOriginalPrice() * quantity)
                    .setScale(2, RoundingMode.HALF_UP).doubleValue();
            this.discountedPrice = BigDecimal.valueOf((product.getOriginalPrice() - product.getFinalPrice()) * quantity)
                    .setScale(2, RoundingMode.HALF_UP).doubleValue();
            this.finalPrice = BigDecimal.valueOf(product.getFinalPrice() * quantity)
                    .setScale(2, RoundingMode.HALF_UP).doubleValue();
        }

        public OrderProductDetails() {
        }
    }
}

