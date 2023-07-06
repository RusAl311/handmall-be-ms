package com.handmall.hmproduct.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.handmall.hmproduct.dtos.ProductRequest;
import com.handmall.hmproduct.dtos.ProductResponse;
import com.handmall.hmproduct.entities.Product;
import com.handmall.hmproduct.mappers.ProductMapper;
import com.handmall.hmproduct.repositories.ProductRepository;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<ProductResponse> getProducts() {
        return productMapper.toProductList(productRepository.findAll());
    }

    public ProductResponse getProduct(String productId) throws NotFoundException {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            throw new NotFoundException("product not found");
        }

        return productMapper.toProduct(product.get());
    }

    public void addNew(ProductRequest productRequest) {
        productRepository.save(productMapper.toProductRequest(productRequest));
    }

    public void delete(String productId) {
        boolean exists = productRepository.existsById(productId);

        if (!exists) {
            throw new IllegalStateException("product with id" + productId + "does not exist");
        }
        
        productRepository.deleteById(productId);
    }
}
