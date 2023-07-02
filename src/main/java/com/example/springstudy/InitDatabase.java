package com.example.springstudy;


import com.example.springstudy.api.model.Company;
import com.example.springstudy.api.model.Country;
import com.example.springstudy.api.model.Employee;
import com.example.springstudy.api.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
public class InitDatabase {

    private static final Logger logger = LoggerFactory.getLogger(InitDatabase.class);


    // https://stacktraceguru.com/springboot/run-method-on-startup
    @Bean
    CommandLineRunner initEmployeeDB(EmployeeRepository employeeRepository) {

        return args -> {
            Company gamaCompany = new Company(1L, "GAMA", Country.POLAND);
            logger.info("Preloading {}", employeeRepository.save(new Employee(1L, "Kris", "Gala", LocalDate.of(1995, 3, 1), BigDecimal.valueOf(5000), gamaCompany)));
            logger.info("Preloading {}", employeeRepository.save(new Employee(2L, "Ann", "Smith", LocalDate.of(2000, 11, 22), BigDecimal.valueOf(15000), gamaCompany)));
            logger.info("Preloading {}", employeeRepository.save(new Employee(3L, "Emma", "Johnson", LocalDate.of(1900, 12, 1), BigDecimal.valueOf(10000.9), gamaCompany)));
        };
    }
}
