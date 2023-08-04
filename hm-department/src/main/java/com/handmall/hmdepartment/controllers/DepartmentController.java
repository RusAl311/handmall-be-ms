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

import com.handmall.hmdepartment.dtos.Department.DepartmentRequest;
import com.handmall.hmdepartment.dtos.Department.DepartmentResponse;
import com.handmall.hmdepartment.entities.Department;
import com.handmall.hmdepartment.services.DepartmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "api/v1/department")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping("/getAll")
    public List<DepartmentResponse> getDepartments() {
        return departmentService.getDepartments();
    }

    @GetMapping("/get/{departmentId}")
    public DepartmentResponse getDepartment(@PathVariable("departmentId") Integer departmentId) {
        return departmentService.getDepartment(departmentId);
    }

    @PostMapping("/addNew")
    public void addNewDepartment(@RequestBody DepartmentRequest departmentRequest) {
        departmentService.addNew(departmentRequest);
    }

    @DeleteMapping("/delete/{departmentId}")
    public void deleteDepartment(@PathVariable("departmentId") Integer departmentId) {
        departmentService.delete(departmentId);
    }

    @PutMapping("/update/{departmentId}")
    public void updateDepartment(@RequestBody DepartmentRequest departmentRequest,
            @PathVariable("departmentId") Integer departmentId) {
        departmentService.update(departmentRequest, departmentId);
    }
}
