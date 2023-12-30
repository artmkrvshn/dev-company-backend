package com.dev.company.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "position")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 chars")
    @NotBlank(message = "Name should not be blank")
    @Column(name = "name")
    private String name;

    public Position(String name) {
        this.name = name;
    }
}
