package com.handmall.hmdepartment.dtos.Category;

public record CategoryRequest(
                                String name,
                                String description,
                                Integer departmentId
) {
}
