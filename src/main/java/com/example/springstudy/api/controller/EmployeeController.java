package com.example.springstudy.api.controller;

import com.example.springstudy.api.EmployeeModelAssembler;
import com.example.springstudy.api.EmployeeNotFoundException;
import com.example.springstudy.api.aspect.GetEmployeeAspect;
import com.example.springstudy.api.model.Employee;
import com.example.springstudy.api.repository.EmployeeRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class EmployeeController {

    EmployeeRepository employeeRepository;

    EmployeeModelAssembler assembler;

    public EmployeeController(EmployeeRepository employeeRepository, EmployeeModelAssembler employeeModelAssembler) {
        this.employeeRepository = employeeRepository;
        this.assembler = employeeModelAssembler;
    }

    @GetMapping("/api/employees")
    public CollectionModel<EntityModel<Employee>> getAll(@RequestParam(required = false) String firstName) {
        List<EntityModel<Employee>> employees = employeeRepository.findAll().stream()
                // should be moved to service / repository
                .filter(employee -> {
                    if (firstName != null)
                        return employee.getFirstName().contains(firstName);
                    return true;
                })
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).getAll(firstName)).withSelfRel());
    }

    @GetMapping("/api/employees2")
    public CollectionModel<EntityModel<Employee>> getAllSearch(Pageable pageable) {

        List<EntityModel<Employee>> employees = employeeRepository.findAll(pageable).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).getAllSearch(pageable)).withSelfRel());
    }

    @GetMapping("/api/employees/{id}")
    @GetEmployeeAspect
    public EntityModel<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        return assembler.toModel(employee);
    }


    @PostMapping("/api/employees")
    public Employee saveEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }
    @PutMapping("/api/employees/{id}")
    public Employee putEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee){
        // should be moved to service
        return employeeRepository.findById(id).
                map(employee -> {
                    employee.setFirstName(updatedEmployee.getFirstName());
                    employee.setLastName(updatedEmployee.getLastName());
                    employee.setBirthday(updatedEmployee.getBirthday());
                    employee.setCompany(updatedEmployee.getCompany());
                    employee.setSalary(updatedEmployee.getSalary());
                    return employeeRepository.save(employee);
                }).orElseGet(() ->{
                    updatedEmployee.setId(id);
                    return employeeRepository.save(updatedEmployee);
                });
    }

    @DeleteMapping("/api/employees/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeRepository.deleteById(id);
    }


}
