package com.example.springstudy.api.repository;


import com.example.springstudy.api.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {

    Employee save(Employee employee);
    List<Employee> findAll();
    Optional<Employee> findById(Long id);
    void deleteById(Long id);
    void deleteAll();
}
