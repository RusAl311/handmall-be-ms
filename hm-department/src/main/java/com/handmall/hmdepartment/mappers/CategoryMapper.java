package com.handmall.hmdepartment.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.handmall.hmdepartment.dtos.Category.CategoryRequest;
import com.handmall.hmdepartment.dtos.Category.CategoryResponse;
import com.handmall.hmdepartment.entities.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper( CategoryMapper.class );
    
    @Mapping(target = "departmentName", source = "department.name")
    CategoryResponse toCategory(Category category);

    List<CategoryResponse> toCategoryList(List<Category> category);

    @Mapping(target = "department.id", source = "departmentId")
    Category toCategoryRequest(CategoryRequest categoryRequest);

}
