package com.handmall.hmdepartment.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.handmall.hmdepartment.dtos.Department.DepartmentRequest;
import com.handmall.hmdepartment.dtos.Department.DepartmentResponse;
import com.handmall.hmdepartment.entities.Department;
import com.handmall.hmdepartment.mappers.DepartmentMapper;
import com.handmall.hmdepartment.repositories.DepartmentRepository;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public List<DepartmentResponse> getDepartments() {
        return departmentMapper.toDepartmentList(departmentRepository.findAll());
    }

    public DepartmentResponse getDepartment(Integer departmentId) throws NotFoundException {
        Optional<Department> department = departmentRepository.findById(departmentId);

        if (department.isEmpty()) {
            throw new NotFoundException("department not found");
        }

        return departmentMapper.toDepartment(department.get());
    }

    public void addNew(DepartmentRequest departmentRequest) {
        Optional<Department> departmentByName = departmentRepository
                .findDepartmentByName(departmentRequest.name());

        if (departmentByName.isPresent()) {
            throw new IllegalStateException("name is exist");
        }
        departmentRepository.save(departmentMapper.toDepartmentRequest(departmentRequest));
    }

    public void delete(Integer departmentId) {
        boolean exists = departmentRepository.existsById(departmentId);
        if (!exists) {
            throw new IllegalStateException("department with id " + departmentId + " does not exist");
        }
        departmentRepository.deleteById(departmentId);
    }

    public void update(DepartmentRequest departmentRequest, Integer departmentId) {
        Optional<Department> departmentById = departmentRepository.findById(departmentId);

        if (departmentById.isEmpty()) {
            throw new IllegalStateException("department with id " + departmentId + " does not exist");
        }

        var department = Department
                .builder()
                .id(departmentId)
                .name(departmentRequest.name())
                .description(departmentRequest.description())
                .build();

        departmentRepository.save(department);
    }
}
