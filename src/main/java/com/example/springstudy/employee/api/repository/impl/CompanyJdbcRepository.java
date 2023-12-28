package com.example.springstudy.employee.api.repository.impl;

import com.example.springstudy.employee.api.model.Company;
import com.example.springstudy.employee.api.repository.CompanyRepository;
import com.example.springstudy.employee.api.repository.impl.mapper.CompanyRowMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Profile("postgres")
@Repository
public class CompanyJdbcRepository implements CompanyRepository {

    private static final String SELECT_COMPANY = "SELECT * FROM COMPANY LIMIT 100";
    private static final String SELECT_COMPANY_BY_ID = "SELECT * FROM COMPANY WHERE COMP_ID = ?";
    private static final String INSERT_COMPANY = "INSERT INTO COMPANY (COMP_ID, COMP_NAME, COMP_COUNTRY) VALUES (?, ?, ?);";
    private static final String DELETE_COMPANY_BY_ID = "DELETE FROM COMPANY WHERE COMP_ID = ?";
    private static final String UPDATE_COMPANY = "UPDATE COMPANY SET COMP_NAME = ?, COMP_COUNTRY = ? WHERE COMP_ID = ?";
    private static final String SELECT_COMPANY_SEQUENCE_NEXTVAL = "SELECT nextval('company_comp_id_seq')";
    JdbcTemplate jdbcTemplate;

    public CompanyJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Company save(Company company) {
        if (company.getId() == null || company.getId() == 0L)
            company.setId(getNextId());

        jdbcTemplate.update(INSERT_COMPANY,
                company.getId(),
                company.getName(),
                company.getCountry().name());
        return company;
    }

    @Override
    public Optional<Company> findById(Long id) {
        return jdbcTemplate.query(SELECT_COMPANY_BY_ID, new CompanyRowMapper(), id)
                .stream()
                .findFirst();
    }

    @Override
    public List<Company> findAll() {
        return jdbcTemplate.query(SELECT_COMPANY, new CompanyRowMapper());
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update(DELETE_COMPANY_BY_ID, id);
    }

    @Override
    public void updateCompany(Long compId, Company company) {
        jdbcTemplate.update(UPDATE_COMPANY, company.getName(), company.getCountry().name(), compId);
    }

    public Long getNextId() {
        return jdbcTemplate.query(SELECT_COMPANY_SEQUENCE_NEXTVAL,
                rs -> {
                    if (rs.next()) {
                        return rs.getLong(1);
                    } else {
                        throw new SQLException("Unable to retrieve value from sequence company_comp_id_seq.");
                    }
                });
    }
}
