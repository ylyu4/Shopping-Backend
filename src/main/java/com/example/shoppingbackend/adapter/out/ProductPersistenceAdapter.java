package com.example.shoppingbackend.adapter.out;

import com.example.shoppingbackend.application.port.out.GetProductDetailPort;
import com.example.shoppingbackend.application.port.out.GetProductListPort;
import com.example.shoppingbackend.domain.constant.ProductStatus;
import com.example.shoppingbackend.domain.Product;
import com.example.shoppingbackend.exception.ProductNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ProductPersistenceAdapter implements GetProductDetailPort, GetProductListPort {

    public static final double MIN_PRICE = 0.01;
    private final ProductRepository productRepository;

    public ProductPersistenceAdapter(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll().stream()
                .filter(it -> Objects.nonNull(it.getPrice()) && it.getProductStatus() == ProductStatus.VALID
                        && it.getStock() > 0).map(Product::new).filter(it -> it.getFinalPrice() >= MIN_PRICE).toList();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).map(Product::new).orElseThrow(() ->
                new ProductNotFoundException(String.format("Can not find product for this id: %s", id)));
    }
}
