package com.example.springstudy.employee.api.repository;


import com.example.springstudy.employee.api.model.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {

    Employee save(Employee employee);
    List<Employee> findAll();

    List<Employee> findByFirstName(String firstName);
    List<Employee> findAll(Pageable pageable);
    List<Employee> findAll(Specification<Employee> where);
    Optional<Employee> findById(Long id);
    int deleteById(Long id);

}
