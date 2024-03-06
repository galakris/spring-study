package com.example.springstudy.configuration;

import com.example.springstudy.employee.api.controller.dto.EmployeeDto;
import com.example.springstudy.employee.api.model.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(Employee.class, EmployeeDto.class)
                .addMappings(mapper -> {
                    mapper.map(Employee::getId, EmployeeDto::setId);
                    mapper.map(Employee::getFirstName, EmployeeDto::setFirstName);
                    mapper.map(Employee::getLastName, EmployeeDto::setLastName);
                    mapper.map(Employee::getBirthDate, EmployeeDto::setBirthDate);
                    mapper.map(emp -> emp.getCompany().getName(), EmployeeDto::setCompany);
                });

        return modelMapper;
    }
}
