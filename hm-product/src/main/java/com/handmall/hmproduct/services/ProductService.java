package com.handmall.hmproduct.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.handmall.hmproduct.entities.Product;
import com.handmall.hmproduct.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }
}
