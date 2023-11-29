package com.example.springstudy.employee.api.repository;

import com.example.springstudy.employee.api.model.Employee;
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

    JdbcTemplate jdbcTemplate;

    CompanyRepository companyRepository;

    @Autowired
    public EmployeeJdbcRepository(JdbcTemplate jdbcTemplate, CompanyRepository companyRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.companyRepository = companyRepository;
    }

    @Override
    public Employee save(Employee employee) {
        String insertEmployee = "INSERT INTO EMPLOYEE VALUES (?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(insertEmployee,
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
        String selectAllEmployees = "SELECT * FROM EMPLOYEE";
        return jdbcTemplate.query(selectAllEmployees,
                (rs, row) -> new Employee(rs.getLong("EMPLOYEE_ID"),
                        rs.getString("FIRST_NAME"),
                        rs.getString("LAST_NAME"),
                        rs.getDate("BIRTH_DATE").toLocalDate(),
                        rs.getBigDecimal("SALARY"),
                        companyRepository.findById(rs.getLong("COMPANY_ID"))));
    }



    @Override
    public List<Employee> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Employee> findAll(Specification<Employee> where) {
        return null;
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void deleteAll() {

    }
}
 	