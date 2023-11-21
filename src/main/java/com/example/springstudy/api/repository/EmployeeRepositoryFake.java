package com.example.springstudy.api.repository;

import com.example.springstudy.api.model.Employee;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Profile("noDB")
@Repository
public class EmployeeRepositoryFake implements EmployeeRepository{

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
    public List<Employee> findAll(Pageable pageable) {
        // TODO: Spring Data -
        throw new NotImplementedException();
    }

    @Override
    public List<Employee> findAll(Specification<Employee> where) {
        // TODO Spring Data - JpaSpecificationExecutor - https://www.javaguides.net/2023/08/spring-data-jpa-specification-example.html
        throw new NotImplementedException();
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst();
    }

    @Override
    public void deleteById(Long id) {
        employees.removeIf(employee -> employee.getId().equals(id));
    }

    @Override
    public void deleteAll() {
        employees.clear();
    }
}
