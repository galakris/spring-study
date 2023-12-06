package com.example.springstudy.employee.api.repository;

import com.example.springstudy.employee.api.model.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository {

    Company save(Company company);

    Optional<Company> findById(Long id);

    List<Company> findAll();

    int deleteById(Long id);

    void updateCompany(Long compId, Company company);
}
