package com.uts.casee.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uts.casee.employee.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
}
