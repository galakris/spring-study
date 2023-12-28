package com.example.springstudy;


import com.example.springstudy.employee.api.model.Company;
import com.example.springstudy.employee.api.model.Country;
import com.example.springstudy.employee.api.model.Employee;
import com.example.springstudy.employee.api.repository.CompanyRepository;
import com.example.springstudy.employee.api.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
public class InitDatabase {

    private static final Logger logger = LoggerFactory.getLogger(InitDatabase.class);


    // https://stacktraceguru.com/springboot/run-method-on-startup
//    @Bean
//    CommandLineRunner initEmployeeDB(EmployeeRepository employeeRepository, CompanyRepository companyRepository, JdbcTemplate jdbcTemplate) {
//
//        return args -> {
//            String dropCompanyTableSql = "DROP TABLE IF EXISTS COMPANY;";
//            jdbcTemplate.update(dropCompanyTableSql);
//            String dropEmployeeTableSql = "DROP TABLE IF EXISTS EMPLOYEE;";
//            jdbcTemplate.update(dropEmployeeTableSql);
//
//            String createCompanyTableSql = "CREATE TABLE COMPANY(COMP_ID INTEGER, COMP_NAME VARCHAR(255), COMP_COUNTRY VARCHAR(255));";
//            jdbcTemplate.update(createCompanyTableSql);
//            String createEmployeeTableSql = "CREATE TABLE EMPLOYEE(EMP_ID INTEGER, EMP_FIRST_NAME VARCHAR(255), EMP_LAST_NAME VARCHAR(255), EMP_BIRTH_DATE TIMESTAMP, EMP_SALARY DECIMAL, COMP_ID INTEGER);";
//            jdbcTemplate.update(createEmployeeTableSql);
//
//            String cleanCompanyTeble = "DELETE FROM COMPANY;";
//            jdbcTemplate.update(cleanCompanyTeble);
//            String cleanEmployeeTeble = "DELETE FROM EMPLOYEE;";
//            jdbcTemplate.update(cleanEmployeeTeble);
//
//            Company gamaCompany = new Company(1L, "GAMA", Country.POLAND);
//            logger.info("Preloading {}", companyRepository.save(gamaCompany));
//            logger.info("Preloading {}", employeeRepository.save(new Employee(1L, "Kris", "Gala", LocalDate.of(1995, 3, 1), BigDecimal.valueOf(5000), gamaCompany)));
//            logger.info("Preloading {}", employeeRepository.save(new Employee(2L, "Ann", "Smith", LocalDate.of(2000, 11, 22), BigDecimal.valueOf(15000), gamaCompany)));
//            logger.info("Preloading {}", employeeRepository.save(new Employee(3L, "Emma", "Johnson", LocalDate.of(1900, 12, 1), BigDecimal.valueOf(10000.9), gamaCompany)));
//        };
//    }
}
