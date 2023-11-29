package com.example.springstudy.employee.api.repository;

import com.example.springstudy.employee.api.model.Company;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Profile("postgres")
@Repository
public class CompanyJdbcRepository implements CompanyRepository {

    JdbcTemplate jdbcTemplate;

    public CompanyJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Company save(Company company) {
        String insertCompany = "INSERT INTO COMPANY VALUES (?, ?, ?);";
        jdbcTemplate.update(insertCompany,
                company.getId(),
                company.getName(),
                company.getCountry().name());
        return company;
    }

    @Override
    public Company findById(Long id) {
        return null;
    }

    @Override
    public List<Company> findAll() {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void deleteAll() {

    }
}
