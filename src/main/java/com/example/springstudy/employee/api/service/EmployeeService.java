package com.example.springstudy.employee.api.service;

import com.example.springstudy.employee.api.model.Employee;
import com.example.springstudy.employee.api.repository.impl.EmployeeJpaRepository;
import com.example.springstudy.employee.api.service.spec.EmployeeSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeService {

    private EmployeeJpaRepository employeeJpaRepository;

    public EmployeeService(EmployeeJpaRepository employeeJpaRepository) {
        this.employeeJpaRepository = employeeJpaRepository;
    }

    public List<Employee> findEmployee(String firstName, String lastName, String companyName, LocalDate from, LocalDate to) {
        Specification<Employee> employeeSpecification = Specification.where((root, query, criteriaBuilder) -> null);

        if (firstName != null)
            employeeSpecification = employeeSpecification.and(EmployeeSpecifications.hasFirstName(firstName));
        if (lastName != null)
            employeeSpecification = employeeSpecification.and(EmployeeSpecifications.hasLastName(lastName));
        if (from != null)
            employeeSpecification = employeeSpecification.and(EmployeeSpecifications.bornAfter(from));
        if (to != null)
            employeeSpecification = employeeSpecification.and(EmployeeSpecifications.bornBefore(to));
        // TODO: specification for companyName
        return employeeJpaRepository.findAll(employeeSpecification);
    }
}
