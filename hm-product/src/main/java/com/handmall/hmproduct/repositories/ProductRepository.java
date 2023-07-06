package com.handmall.hmproduct.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.handmall.hmproduct.entities.Product;

public interface ProductRepository extends MongoRepository<Product, String>{
    
}
