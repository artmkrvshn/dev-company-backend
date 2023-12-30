package com.dev.company.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class PositionDTO {

    private UUID id;
    private String name;
}
