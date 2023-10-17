package com.example.shoppingbackend.adapter.in;

import com.example.shoppingbackend.application.port.in.GetProductListUseCase;
import com.example.shoppingbackend.domain.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final GetProductListUseCase getProductListUseCase;

    public ProductController(GetProductListUseCase getProductListUseCase) {
        this.getProductListUseCase = getProductListUseCase;
    }

    @GetMapping("/list")
    public List<Product> getProductList() {
        return getProductListUseCase.getProductList();
    }
}
