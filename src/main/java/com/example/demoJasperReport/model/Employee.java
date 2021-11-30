package com.example.demoJasperReport.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee {

    private final Integer id;
    private final String name;
    private final String designation;
    private final String department;

}
