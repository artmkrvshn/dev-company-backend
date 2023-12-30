package com.dev.company.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Size(min = 2, max = 20, message = "Name should be between 2 and 20 chars")
    @Column(name = "name")
    private String name;

    @Size(min = 2, max = 20, message = "Surname should be between 2 and 20 chars")
    @Column(name = "surname")
    private String surname;

    @PositiveOrZero(message = "Minimal salary should be more than 0")
    @Column(name = "salary")
    private double salary;

    @NotEmpty(message = "Email should not be empty")
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "Password should not be empty")
    @Column(name = "password")
    private String password;

    @NotNull(message = "Department shouldn't be null")
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @NotNull(message = "Position shouldn't be null")
    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

    @ToString.Exclude
    @ManyToMany(mappedBy = "employees")
    private List<Project> projects = new ArrayList<>();
}
