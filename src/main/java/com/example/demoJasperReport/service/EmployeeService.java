package com.example.demoJasperReport.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.demoJasperReport.model.Employee;
import org.springframework.stereotype.Service;
import com.github.javafaker.Faker;

@Service
public class EmployeeService{

    final Faker faker = new Faker();
    final Random random = new Random();

    // For ease we are not making the database interaction here.
    // Readers can inject the dao layer here to make the real-time database interactions.
    public List<Employee> findAll() {
        final List<Employee> employees = new ArrayList<>();
        // Creating a list of employees using the "faker" object.
        for(int count=0; count<21; count++) {
            employees.add(new Employee(random.nextInt(30 + 1), faker.name().fullName(),
                    faker.job().title(), faker.job().field()));
        }
        return employees;
    }
}

