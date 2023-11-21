package com.example.springstudy.api.service;

import com.example.springstudy.api.model.Employee;
import com.example.springstudy.api.repository.EmployeeRepository;
import com.example.springstudy.api.service.spec.EmployeeSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findEmployee(String firstName, String lastName, LocalDate from, LocalDate to) {
        return employeeRepository.findAll(Specification
                .where(EmployeeSpecifications.hasFirstName(firstName)
                .and(EmployeeSpecifications.hasLastName(lastName)
                .and(EmployeeSpecifications.bornAfter(from))
                .and(EmployeeSpecifications.bornBefore(to)))));
    }
}
