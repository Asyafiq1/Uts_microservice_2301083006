package com.uts.casee.employee.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uts.casee.employee.model.Employee;
import com.uts.casee.employee.service.EmployeeService;


@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    
    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

     // Endpoint untuk mengambil semua employee
    @GetMapping
    public List<Employee> getAllEmployee() {
        log.info("GET /api/employees accessed");
        return employeeService.getAll();
    }

    // Endpoint untuk mengambil employee berdasarkan id
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        log.info("GET /api/employees/{} accessed", id);
            return employeeService.getById(id)
                .map(employee -> ResponseEntity.ok().body(employee))
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint membuat employee baru
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        log.info("POST /api/employees accessed with body: {}", employee);
            return employeeService.createEmployee(employee);
    }

     // Endpoint untuk update employee yang sudah ada
     @PutMapping("/{id}")
     public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
         log.info("PUT /api/employees/{} accessed with body: {}", id, employeeDetails);
 
         try {
             Employee updateEmployee = employeeService.updateEmployee(id, employeeDetails);
             return ResponseEntity.ok(updateEmployee);
         } catch (RuntimeException e) {
             log.warn("PUT /api/employees/{} failed: {}", id, e.getMessage());
             return ResponseEntity.notFound().build();
         }
     }

     // Endpoint DELETE item
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
    log.info("DELETE /api/employees/{} accessed", id);
    Map<String, String> response = new HashMap<>();
        try {
            employeeService.deleteEmployee(id);
            response.put("message", "Employee berhasil dihapus");
            return ResponseEntity.ok("Employee deleted successfully");
        } catch (RuntimeException e) {
            response.put("message", "Employee tidak ditemukan dengan id" + id);
            log.warn("DELETE /api/employees/{} failed: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
 
}
