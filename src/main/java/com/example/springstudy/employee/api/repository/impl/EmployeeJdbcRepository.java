package com.example.springstudy.employee.api.repository.impl;

import com.example.springstudy.employee.api.model.Employee;
import com.example.springstudy.employee.api.repository.EmployeeRepository;
import com.example.springstudy.employee.api.repository.impl.mapper.EmployeeRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Profile("postgres")
@Repository
public class EmployeeJdbcRepository implements EmployeeRepository {

    private static final String INSERT_EMP = "INSERT INTO EMPLOYEE (EMP_ID, EMP_FIRST_NAME, EMP_LAST_NAME, EMP_BIRTH_DATE, EMP_SALARY, COMP_ID) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String SELECT_EMP = "SELECT * FROM EMPLOYEE E JOIN COMPANY C ON E.COMP_ID = C.COMP_ID LIMIT 100";
    private static final String SELECT_EMP_BY_ID = "SELECT * FROM EMPLOYEE E JOIN COMPANY C ON E.COMP_ID = C.COMP_ID WHERE E.EMP_ID = ?";
    private static final String SELECT_EMP_BY_FIRST_NAME = "SELECT * FROM EMPLOYEE E JOIN COMPANY C ON E.COMP_ID = C.COMP_ID WHERE E.EMP_FIRST_NAME = ?";
    private static final String DELETE_EMP = "DELETE FROM EMPLOYEE WHERE EMP_ID = ?";
    private static final String SELECT_EMP_SEQUENCE_NEXTVAL = "SELECT nextval('employee_emp_id_seq')";

    JdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // if JdbcTemplate Bean not created in configuration file
//    @Autowired
//    public EmployeeJdbcRepository(DataSource dataSource) {
//        this.jdbcTemplate = new JdbcTemplate(dataSource);
//    }

    @Override
    public Employee save(Employee employee) {
        if (employee.getId() == null || employee.getId() == 0L)
            employee.setId(getNextId());

        int update = jdbcTemplate.update(INSERT_EMP,
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

    public Long getNextId() {
        return jdbcTemplate.query(SELECT_EMP_SEQUENCE_NEXTVAL,
                rs -> {
                    if (rs.next()) {
                        return rs.getLong(1);
                    } else {
                        throw new SQLException("Unable to retrieve value from sequence employee_emp_id_seq.");
                    }
                });
    }
}
 	