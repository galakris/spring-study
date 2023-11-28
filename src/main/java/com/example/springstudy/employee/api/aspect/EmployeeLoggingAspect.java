package com.example.springstudy.employee.api.aspect;

import com.example.springstudy.employee.api.model.Employee;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EmployeeLoggingAspect {

    @Pointcut("execution(* com.example.springstudy.employee.api.controller.EmployeeController.getEmployeeById(Long))")
    public void logPointcut() {}

    @Around("@annotation(GetEmployeeAspect)")
    public Object aroundGetEmployee(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("EmployeeController.getEmployeeById - around");
        return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
    }

    @Before("@annotation(GetEmployeeAspect)")
    public void beforeGetEmployee(JoinPoint joinPoint) {
        System.out.println("EmployeeController.getEmployeeById before: " + joinPoint.getArgs()[0]);
    }

    @AfterReturning(pointcut = "@annotation(GetEmployeeAspect)",
    returning = "result")
    public void afterEmployeeSuccess(JoinPoint joinPoint, EntityModel<Employee> result) {
        System.out.println("EmployeeController.getEmployeeById - result " + result.getContent());
    }

    @AfterThrowing("logPointcut()")
    public void getEmployeeException() {
        System.out.println("EmployeeController.getEmployeeById - exception ");
    }
}
