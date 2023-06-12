package com.handmall.hmdepartment.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.handmall.hmdepartment.dtos.Category.CategoryRequest;
import com.handmall.hmdepartment.dtos.Category.CategoryResponse;
import com.handmall.hmdepartment.services.CategoryService;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/getAll")
    public List<CategoryResponse> getCategories() {
        return categoryService.getCategories();
    }

    @GetMapping("/get/{categoryId}")
    public CategoryResponse getCategory(@PathVariable("categoryId") Integer categoryId) throws NotFoundException {
        return categoryService.getCategory(categoryId);
    }

    @PostMapping("addNew")
    public void addNewCategory(@RequestBody CategoryRequest category) {
        categoryService.addNew(category);
    }

    @PutMapping("/update/{categoryId}")
    public void updateCategory(@RequestBody CategoryRequest categoryRequest) {
        categoryService.update(categoryRequest);
    }

    @DeleteMapping("/delete/{categoryId}")
    public void deleteCategory(@PathVariable("categoryId") Integer categoryId) {
        categoryService.delete(categoryId);
    }
}
