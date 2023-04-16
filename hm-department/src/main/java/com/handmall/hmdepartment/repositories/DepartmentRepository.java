package com.handmall.hmdepartment.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.handmall.hmdepartment.entities.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer>{
    Optional<Department> findDepartmentByName(String name);
}
