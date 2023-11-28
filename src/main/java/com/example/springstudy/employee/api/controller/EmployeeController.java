package com.example.springstudy.employee.api.controller;

import com.example.springstudy.employee.api.EmployeeModelAssembler;
import com.example.springstudy.employee.api.exception.EmployeeAlreadyExistException;
import com.example.springstudy.employee.api.exception.EmployeeNotFoundException;
import com.example.springstudy.employee.api.aspect.GetEmployeeAspect;
import com.example.springstudy.employee.api.model.Employee;
import com.example.springstudy.employee.api.repository.EmployeeRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("api/v1/employees")
public class EmployeeController {

    EmployeeRepository employeeRepository;

    EmployeeModelAssembler assembler;

    public EmployeeController(EmployeeRepository employeeRepository, EmployeeModelAssembler employeeModelAssembler) {
        this.employeeRepository = employeeRepository;
        this.assembler = employeeModelAssembler;
    }

    @GetMapping
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

    // TODO: implement pagination
    @GetMapping("/pageable")
    public CollectionModel<EntityModel<Employee>> getAllSearch(Pageable pageable) {

        List<EntityModel<Employee>> employees = employeeRepository.findAll(pageable).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).getAllSearch(pageable)).withSelfRel());
    }

    @GetMapping("{id}")
    @GetEmployeeAspect
    public EntityModel<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        return assembler.toModel(employee);
    }


    @PostMapping
    public ResponseEntity<EntityModel<Employee>> saveEmployee(@RequestBody Employee employee) {
        employeeRepository.findById(employee.getId()).ifPresent(emp -> {
            // maybe link should be added to error message
            throw new EmployeeAlreadyExistException(emp.getId());
        });
        EntityModel<Employee> employeeEntityModel = assembler.toModel(employeeRepository.save(employee));
        return ResponseEntity.created(employeeEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(employeeEntityModel);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> putEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        // should be moved to service
        Employee updatedEmployee = employeeRepository.findById(id).
                map(emp -> {
                    emp.setFirstName(employee.getFirstName());
                    emp.setLastName(employee.getLastName());
                    emp.setBirthday(employee.getBirthday());
                    emp.setCompany(employee.getCompany());
                    emp.setSalary(employee.getSalary());
                    return employeeRepository.save(emp);
                }).orElseGet(() -> {
                    employee.setId(id);
                    return employeeRepository.save(employee);
                });
        EntityModel<Employee> employeeEntityModel = assembler.toModel(updatedEmployee);
        return ResponseEntity.ok(employeeEntityModel);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        employeeRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find employee with id: " + id));
        employeeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
