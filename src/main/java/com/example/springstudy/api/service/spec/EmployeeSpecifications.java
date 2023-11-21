package com.example.springstudy.api.service.spec;

import com.example.springstudy.api.model.Employee;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class EmployeeSpecifications {

    private EmployeeSpecifications() {
    }

    public static Specification<Employee> hasFirstName(String firstName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("firstName"), "%" + firstName + "%");
    }

    public static Specification<Employee> hasLastName(String lastName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("lastName"), "%" + lastName + "%");
    }

    public static Specification<Employee> bornAfter(LocalDate date) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("birthday"), date);
    }

    public static Specification<Employee> bornBefore(LocalDate date) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("birthday"), date);
    }



}
