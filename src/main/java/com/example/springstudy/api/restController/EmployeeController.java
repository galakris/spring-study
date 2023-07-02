package com.example.springstudy.api.restController;

import com.example.springstudy.api.EmployeeNotFoundException;
import com.example.springstudy.api.model.Employee;
import com.example.springstudy.api.repository.CarRepository;
import com.example.springstudy.api.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeController {

    EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/api/employees")
    List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @PostMapping("/api/employees")
    Employee saveEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @GetMapping("/api/employees/{id}")
    Employee getEmployeeById(@PathVariable Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @PutMapping("/api/employees/{id}")
    Employee putEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee){
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
    void deleteEmployee(@PathVariable Long id) {
        employeeRepository.deleteById(id);
    }


}
