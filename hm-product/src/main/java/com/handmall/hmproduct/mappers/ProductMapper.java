package com.handmall.hmproduct.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.handmall.hmproduct.dtos.ProductRequest;
import com.handmall.hmproduct.dtos.ProductResponse;
import com.handmall.hmproduct.entities.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductResponse toProduct(Product product);
    
    List<ProductResponse> toProductList(List<Product> product);

    Product toProductRequest(ProductRequest productRequest);
}
