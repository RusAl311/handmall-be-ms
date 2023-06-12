package com.handmall.hmdepartment.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.handmall.hmdepartment.dtos.Category.CategoryRequest;
import com.handmall.hmdepartment.dtos.Category.CategoryResponse;
import com.handmall.hmdepartment.entities.Category;
import com.handmall.hmdepartment.mappers.CategoryMapper;
import com.handmall.hmdepartment.repositories.CategoryRepository;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryResponse> getCategories() {
        return categoryMapper.toCategoryList(categoryRepository.findAll());
    }

    public CategoryResponse getCategory(Integer categoryId) throws NotFoundException {
        Optional<Category> category = categoryRepository.findById(categoryId);

        if (category.isEmpty()) {
            throw new NotFoundException("category not found");
        }

        return categoryMapper.toCategory(category.get());
    }

    public void addNew(CategoryRequest categoryRequest) {
        Optional<Category> categoryByName = categoryRepository
                .findCategoryByName(categoryRequest.name());

        if (categoryByName.isPresent()) {
            throw new IllegalStateException("name is exist");
        }

        categoryRepository.save(categoryMapper.toCategoryRequest(categoryRequest));
    }

    public void update(CategoryRequest categoryRequest) {
        Optional<Category> updateCategoryRow = categoryRepository.findById(categoryRequest.id());

        if (updateCategoryRow.isPresent()) {
            categoryRepository.save(categoryMapper.toCategoryRequest(categoryRequest));
        } else {
            throw new IllegalStateException("category with id " + categoryRequest.id() + " does not exist");
        }
    }

    public void delete(Integer categoryId) {
        boolean exists = categoryRepository.existsById(categoryId);
        if (!exists) {
            throw new IllegalStateException("category with id " + categoryId + " does not exist");
        }
        categoryRepository.deleteById(categoryId);
    }
}
