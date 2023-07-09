package com.handmall.hmproduct.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.handmall.hmproduct.dtos.ProductRequest;
import com.handmall.hmproduct.dtos.ProductResponse;
import com.handmall.hmproduct.services.ProductService;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/getAll")
    public List<ProductResponse> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/getAllByCategory/{categoryId}")
    public List<ProductResponse> getProductsByCategory(@PathVariable("categoryId") Integer categoryId) {
        return productService.getProductsByCategory(categoryId);
    }

    @GetMapping("/get/{productId}")
    public ProductResponse getProduct(@PathVariable("productId") String productId) throws NotFoundException {
        return productService.getProduct(productId);
    }

    @PostMapping("addNew")
    public void addNewProduct(@RequestBody ProductRequest product) {
        productService.addNew(product);
    }

    @DeleteMapping("/delete/{productId}")
    public void deleteProduct(@PathVariable("productId") String productId) {
        productService.delete(productId);
    }
}
