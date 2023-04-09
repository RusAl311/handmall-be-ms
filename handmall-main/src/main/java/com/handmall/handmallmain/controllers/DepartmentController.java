package com.handmall.handmallmain.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.handmall.handmallmain.entities.Department;
import com.handmall.handmallmain.services.DepartmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "api/v1/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public List<Department> getDepartments() {
        return departmentService.getDepartments();
    }
    
    @PostMapping
    public void addNewDepartment(@RequestBody Department department) {
        departmentService.addNew(department);
    }

    @DeleteMapping(path = "{departmentId}")
    public void deleteDepartment(@PathVariable("departmentId") Long departmentId) {
        departmentService.delete(departmentId);
    }

    @PutMapping
    public void updateDepartment(@RequestBody Department department) {
        departmentService.update(department);
    }
}

