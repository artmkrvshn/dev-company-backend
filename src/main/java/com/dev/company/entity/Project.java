package com.dev.company.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "project")
@SecondaryTable(name = "project_chief",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "project_id", referencedColumnName = "id"))
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @OneToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @OneToOne()
    @JoinColumn(name = "employee_id", table = "project_chief")
    private Employee chief;

    @Builder.Default
    @ManyToMany(cascade = {CascadeType.ALL, CascadeType.PERSIST})
    @JoinTable(name = "project_employees",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private List<Employee> employees = new ArrayList<>();

}
