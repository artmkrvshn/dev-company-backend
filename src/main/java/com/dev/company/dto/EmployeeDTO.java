package com.dev.company.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class EmployeeDTO {

    private UUID id;
    private String name;
    private String surname;
    private double salary;
    private String email;
    private String password;
    private DepartmentDTO department;
    private PositionDTO position;
}
