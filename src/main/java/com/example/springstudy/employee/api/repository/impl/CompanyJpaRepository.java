package com.example.springstudy.employee.api.repository.impl;

import com.example.springstudy.employee.api.model.Company;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Profile("hibernate")
@Repository
public interface CompanyJpaRepository extends JpaRepository<Company, Long> {
}
