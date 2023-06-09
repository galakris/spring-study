package com.example.springstudy.api.model;

import java.util.List;

public class Company {

    private Long id;

    private String name;
    private Country country;
    private List<Employee> employees;

    public Company() {
    }

    public Company(Long id, String name, Country country) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.employees = employees;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
