package com.dev.company.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class ProjectDTO {

    private UUID id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private StatusDTO status;
    private EmployeeDTO chief;
}
