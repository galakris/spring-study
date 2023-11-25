package com.example.springstudy.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Optional;

@ControllerAdvice
public class EmployeeResponseExceptionHandler {
    @ResponseBody
    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage employeeNotFoundHandler(EmployeeNotFoundException e){
        return new ErrorMessage(HttpStatus.NOT_FOUND.value(), e.getMessage(), "Ensure the provided employee ID is correct");
    }

    @ExceptionHandler(EmployeeAlreadyExistException.class)
    public ResponseEntity<ErrorMessage> employeeAlreadyExistHandler(EmployeeAlreadyExistException e) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND.value(), e.getMessage(), "Ensure the provided employee ID is not used");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }
}
