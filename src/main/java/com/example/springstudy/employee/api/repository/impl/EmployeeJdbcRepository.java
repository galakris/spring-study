package com.example.springstudy.employee.api.repository.impl;

import com.example.springstudy.employee.api.model.Employee;
import com.example.springstudy.employee.api.repository.CompanyRepository;
import com.example.springstudy.employee.api.repository.EmployeeRepository;
import com.example.springstudy.employee.api.repository.impl.mapper.EmployeeRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Profile("postgres")
@Repository
public class EmployeeJdbcRepository implements EmployeeRepository {

    private static final String INSERT_EMP = "INSERT INTO EMPLOYEE VALUES (?, ?, ?, ?, ?, ?);";
    private static final String SELECT_EMP = "SELECT * FROM EMPLOYEE E JOIN COMPANY C ON E.COMP_ID = C.COMP_ID LIMIT 100";
    private static final String SELECT_EMP_BY_ID = "SELECT * FROM EMPLOYEE E JOIN COMPANY C ON E.COMP_ID = C.COMP_ID WHERE E.EMP_ID = ?";
    private static final String SELECT_EMP_BY_FIRST_NAME = "SELECT * FROM EMPLOYEE E JOIN COMPANY C ON E.COMP_ID = C.COMP_ID WHERE E.EMP_FIRST_NAME = ?";
    private static final String DELETE_EMP = "DELETE FROM EMPLOYEE WHERE EMP_ID = ?";

    JdbcTemplate jdbcTemplate;

    CompanyRepository companyRepository;

    @Autowired
    public EmployeeJdbcRepository(JdbcTemplate jdbcTemplate, CompanyRepository companyRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.companyRepository = companyRepository;
    }

    @Override
    public Employee save(Employee employee) {
        jdbcTemplate.update(INSERT_EMP,
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getBirthday(),
                employee.getSalary(),
                employee.getCompany().getId());
        return employee;
    }

    @Override
    public List<Employee> findAll() {
        return jdbcTemplate.query(SELECT_EMP, new EmployeeRowMapper());
    }

    @Override
    public List<Employee> findByFirstName(String firstName) {
        return jdbcTemplate.query(SELECT_EMP_BY_FIRST_NAME, new EmployeeRowMapper(), firstName);
    }

    @Override
    public List<Employee> findAll(Pageable pageable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public List<Employee> findAll(Specification<Employee> where) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return jdbcTemplate.query(SELECT_EMP_BY_ID, new EmployeeRowMapper(), id).stream().findFirst();
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update(DELETE_EMP,  id);
    }
}
 	