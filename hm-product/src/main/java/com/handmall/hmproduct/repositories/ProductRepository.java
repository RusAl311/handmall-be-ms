package com.handmall.hmproduct.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.handmall.hmproduct.entities.Product;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String>{
    @Query("{categoryId: ?0}")
    Optional<List<Product>> findByCategory(Integer categoryId);
}
