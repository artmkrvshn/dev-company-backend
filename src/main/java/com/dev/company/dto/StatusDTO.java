package com.dev.company.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class StatusDTO {

    private UUID id;
    private String name;
}
