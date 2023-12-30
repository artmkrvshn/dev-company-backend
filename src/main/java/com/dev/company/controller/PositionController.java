package com.dev.company.controller;

import com.dev.company.aspect.TrackExecutionTime;
import com.dev.company.dto.PositionDTO;
import com.dev.company.entity.Position;
import com.dev.company.mapper.PositionMapper;
import com.dev.company.service.PositionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@TrackExecutionTime
@RestController
@RequestMapping("/api/v1/positions")
public class PositionController {

    private final PositionService service;
    private final PositionMapper mapper;

    public PositionController(PositionService service, PositionMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<PositionDTO>> readAll(@RequestParam(defaultValue = "id") String sort, @RequestParam(defaultValue = "asc") Sort.Direction direction) {
        List<Position> positions = service.readAll();
        List<PositionDTO> responses = mapper.map(positions);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PositionDTO> readById(@PathVariable(name = "id") UUID id) {
        Position position = service.readById(id);
        PositionDTO response = mapper.map(position);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<PositionDTO> create(@RequestBody @Valid Position position) {
        Position created = service.create(position);
        PositionDTO response = mapper.map(created);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") UUID id, @RequestBody @Valid Position position) {
        service.updateById(id, position);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
