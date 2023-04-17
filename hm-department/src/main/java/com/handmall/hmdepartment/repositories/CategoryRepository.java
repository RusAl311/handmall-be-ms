package com.handmall.hmdepartment.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.handmall.hmdepartment.entities.Category;


public interface CategoryRepository extends JpaRepository<Category, Integer>{
    Optional<Category> findCategoryByName(String name);    
}
