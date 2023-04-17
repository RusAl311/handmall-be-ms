package com.handmall.hmdepartment.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.handmall.hmdepartment.entities.Category;
import com.handmall.hmdepartment.repositories.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public void addNew(Category category) {
        Optional<Category> categoryByName = categoryRepository
                .findCategoryByName(category.getName());

        if (categoryByName.isPresent()) {
            throw new IllegalStateException("name is exist");
        }
        categoryRepository.save(category);
    }

    public void delete(Integer categoryId) {
        boolean exists = categoryRepository.existsById(categoryId);
        if (!exists) {
            throw new IllegalStateException("category with id " + categoryId + " does not exist");
        }
        categoryRepository.deleteById(categoryId);
    }
}
