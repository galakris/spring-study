package com.example.springstudy.employee.api.repository.impl.mapper;

import com.example.springstudy.employee.api.model.Company;
import com.example.springstudy.employee.api.model.Employee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeRowMapper implements RowMapper<Employee> {
    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        CompanyRowMapper companyRowMapper = new CompanyRowMapper();

        return new Employee(rs.getLong("EMP_ID"),
                rs.getString("EMP_FIRST_NAME"),
                rs.getString("EMP_LAST_NAME"),
                rs.getDate("EMP_BIRTH_DATE").toLocalDate(),
                rs.getBigDecimal("EMP_SALARY"),
                companyRowMapper.mapRow(rs, rowNum));
    }
}
