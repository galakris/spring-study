package com.example.springstudy.employee.api.controller;

import com.example.springstudy.employee.api.EmployeeModelAssembler;
import com.example.springstudy.employee.api.controller.dto.EmployeeDto;
import com.example.springstudy.employee.api.exception.EmployeeAlreadyExistException;
import com.example.springstudy.employee.api.exception.EmployeeNotFoundException;
import com.example.springstudy.employee.api.aspect.GetEmployeeAspect;
import com.example.springstudy.employee.api.model.Employee;
import com.example.springstudy.employee.api.repository.impl.EmployeeJpaRepository;
import com.example.springstudy.employee.api.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "api/v1/employees")
public class EmployeeController {

    EmployeeJpaRepository employeeJpaRepository;

    EmployeeService employeeService;

    EmployeeModelAssembler assembler;

    ModelMapper modelMapper;

    public EmployeeController(EmployeeJpaRepository employeeJpaRepository, EmployeeService employeeService, EmployeeModelAssembler assembler, ModelMapper modelMapper) {
        this.employeeJpaRepository = employeeJpaRepository;
        this.employeeService = employeeService;
        this.assembler = assembler;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public CollectionModel<EntityModel<Employee>> getAll(@RequestParam(required = false) String firstName) {
        List<EntityModel<Employee>> employees = employeeJpaRepository.findAll().stream()
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

    @GetMapping("/dto")
    public ResponseEntity<List<EmployeeDto>> getAllDto(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String company,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @SortDefault("id") @PageableDefault(5) Pageable pageable) {
        List<EmployeeDto> employees = employeeService.findEmployee(firstName, lastName, company, from, to, pageable)
                .stream()
                .map(emp -> modelMapper.map(emp, EmployeeDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(employees);
    }

    // TODO: implement pagination
    @GetMapping("/pageable")
    public CollectionModel<EntityModel<Employee>> getAllSearch(Pageable pageable) {

        List<EntityModel<Employee>> employees = employeeJpaRepository.findAll(pageable).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).getAllSearch(pageable)).withSelfRel());
    }

    @GetMapping("{id}")
    @GetEmployeeAspect
    public EntityModel<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeJpaRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        return assembler.toModel(employee);
    }


    @PostMapping
    public ResponseEntity<EntityModel<Employee>> saveEmployee(@RequestBody Employee employee) {
        employeeJpaRepository.findById(employee.getId()).ifPresent(emp -> {
            // maybe link should be added to error message
            throw new EmployeeAlreadyExistException(emp.getId());
        });
        EntityModel<Employee> employeeEntityModel = assembler.toModel(employeeJpaRepository.save(employee));
        return ResponseEntity.created(employeeEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(employeeEntityModel);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> putEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        // should be moved to service
        Employee updatedEmployee = employeeJpaRepository.findById(id).
                map(emp -> {
                    emp.setFirstName(employee.getFirstName());
                    emp.setLastName(employee.getLastName());
                    emp.setBirthDate(employee.getBirthDate());
                    emp.setCompany(employee.getCompany());
                    emp.setSalary(employee.getSalary());
                    return employeeJpaRepository.save(emp);
                }).orElseGet(() -> {
                    employee.setId(id);
                    return employeeJpaRepository.save(employee);
                });
        EntityModel<Employee> employeeEntityModel = assembler.toModel(updatedEmployee);
        return ResponseEntity.ok(employeeEntityModel);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        employeeJpaRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find employee with id: " + id));
        employeeJpaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
