package com.example.springstudy.employee.api.exception;

public class EmployeeAlreadyExistException extends RuntimeException {
    public EmployeeAlreadyExistException(Long id) {
        super("Employee with given ID already exist: " + id);
    }
}
