package com.example.springstudy.employee.api.repository;

import com.example.springstudy.employee.api.model.Company;

import java.util.List;

public interface CompanyRepository {

    Company save(Company company);

    Company findById(Long id);
    List<Company> findAll();
    void deleteById(Long id);
    void deleteAll();
}
