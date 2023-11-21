package com.example.springstudy.api.repository;


import com.example.springstudy.api.model.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {

    Employee save(Employee employee);
    List<Employee> findAll();
    List<Employee> findAll(Pageable pageable);
    List<Employee> findAll(Specification<Employee> where);
    Optional<Employee> findById(Long id);
    void deleteById(Long id);
    void deleteAll();

}
