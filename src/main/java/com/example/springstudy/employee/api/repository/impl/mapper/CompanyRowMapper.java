package com.example.springstudy.employee.api.repository.impl.mapper;

import com.example.springstudy.employee.api.model.Company;
import com.example.springstudy.employee.api.model.Country;
import org.springframework.jdbc.core.RowMapper;

import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CompanyRowMapper implements RowMapper<Company> {

    @Override
    public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Company(
                rs.getLong("COMP_ID"),
                rs.getString("COMP_NAME"),
                Country.valueOf(rs.getString("COMP_COUNTRY")),
                new ArrayList<>());
    }
}
