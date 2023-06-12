package com.handmall.hmdepartment.dtos.Category;

public record CategoryRequest(
                                Integer id,
                                String name,
                                String description,
                                Integer departmentId
) {
}
