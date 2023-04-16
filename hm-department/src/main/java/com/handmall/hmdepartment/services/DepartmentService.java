package com.handmall.hmdepartment.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.handmall.hmdepartment.entities.Department;
import com.handmall.hmdepartment.repositories.DepartmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    public void addNew(Department department) {
        Optional<Department> departmentByName = departmentRepository
                .findDepartmentByName(department.getName());
        
        if (departmentByName.isPresent()) {
            throw new IllegalStateException("name is exist");
        }
        departmentRepository.save(department);
    }

    public void delete(Integer departmentId) {
        boolean exists = departmentRepository.existsById(departmentId);
        if (!exists) {
            throw new IllegalStateException("department with id " + departmentId + " does not exist");
        }
        departmentRepository.deleteById(departmentId);
    }

    public void update(Department department) {
        Optional<Department> updatedDepartmentRow = departmentRepository.findById(department.getId());
        if (updatedDepartmentRow.isPresent()) {
            Department updatedDepartment = updatedDepartmentRow.get();
            if (!department.getName().isEmpty()) {
                updatedDepartment.setName(department.getName());
            }
            departmentRepository.save(updatedDepartment);
        } else {
            throw new IllegalStateException("department with id " + department.getId() + " does not exist");
        }
    }
}
