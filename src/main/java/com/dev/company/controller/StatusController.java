package com.dev.company.controller;

import com.dev.company.aspect.TrackExecutionTime;
import com.dev.company.dto.StatusDTO;
import com.dev.company.entity.Status;
import com.dev.company.mapper.StatusMapper;
import com.dev.company.service.StatusService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@TrackExecutionTime
@Log4j2
@Controller
@RequestMapping("/api/v1/statuses")
public class StatusController {

    private final StatusService service;
    private final StatusMapper mapper;

    public StatusController(StatusService service, StatusMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<StatusDTO>> readAll(@RequestParam(defaultValue = "id") String sort, @RequestParam(defaultValue = "asc") Sort.Direction direction) {
        List<Status> statuses = service.readAll(Sort.by(direction, sort));
        List<StatusDTO> responses = mapper.map(statuses);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StatusDTO> readById(@PathVariable(name = "id") UUID id) {
        Status status = service.readById(id);
        StatusDTO response = mapper.map(status);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<StatusDTO> create(@RequestBody @Valid Status status) {
        Status created = service.create(status);
        StatusDTO response = mapper.map(created);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") UUID id, @RequestBody @Valid Status status) {
        service.updateById(id, status);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
