package com.example.springstudy.employee.api.repository.impl;

import com.example.springstudy.employee.api.model.Employee;

import com.example.springstudy.employee.api.repository.EmployeeRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Profile("noDB")
@Repository
public class EmployeeRepositoryFake implements EmployeeRepository {

    List<Employee> employees = new ArrayList<>();

    @Override
    public Employee save(Employee employee) {
        employees.add(employee);
        return employee;
    }

    @Override
    public List<Employee> findAll() {
        return employees;
    }

    @Override
    public List<Employee> findByFirstName(String firstName) {
        if(firstName != null && !firstName.isEmpty()) {
            return employees
                    .stream()
                    .filter(employee -> firstName.equals(employee.getFirstName()))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public List<Employee> findAll(Pageable pageable) {
        // TODO: Spring Data -
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public List<Employee> findAll(Specification<Employee> where) {
        // TODO Spring Data - JpaSpecificationExecutor - https://www.javaguides.net/2023/08/spring-data-jpa-specification-example.html
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst();
    }

    @Override
    public int deleteById(Long id) {
        return employees.removeIf(employee -> employee.getId().equals(id)) ? 1 : 0;
    }
}
