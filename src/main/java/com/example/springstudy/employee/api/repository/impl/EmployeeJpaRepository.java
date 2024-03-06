package com.example.springstudy.employee.api.repository.impl;

import com.example.springstudy.employee.api.model.Employee;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Profile("hibernate")
@Repository
public interface EmployeeJpaRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
}
