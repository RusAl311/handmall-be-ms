package com.handmall.hmdepartment.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.handmall.hmdepartment.dtos.Department.DepartmentRequest;
import com.handmall.hmdepartment.dtos.Department.DepartmentResponse;
import com.handmall.hmdepartment.entities.Department;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    
    DepartmentResponse toDepartment(Department department);

    List<DepartmentResponse> toDepartmentList(List<Department> departments);

    Department toDepartmentRequest(DepartmentRequest departmentRequest);
}
