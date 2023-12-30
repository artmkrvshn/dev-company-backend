package com.dev.company.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ProjectDetailedDTO {

    private UUID id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private StatusDTO status;
    private EmployeeDTO chief;
    private List<EmployeeDTO> employees;
}
