package com.example.shoppingbackend.adapter.in;

import com.example.shoppingbackend.domain.Product;
import com.example.shoppingbackend.application.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/list")
    public List<Product> getProductList() {
        return productService.getProductList();
    }
}
