package com.dev.company.controller;

import com.dev.company.aspect.TrackExecutionTime;
import com.dev.company.dto.ProjectDetailedDTO;
import com.dev.company.dto.ProjectDTO;
import com.dev.company.entity.Project;
import com.dev.company.mapper.ProjectMapper;
import com.dev.company.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@TrackExecutionTime
@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private final ProjectService service;
    private final ProjectMapper mapper;

    @Autowired
    public ProjectController(ProjectService service, ProjectMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> readAll(@RequestParam(defaultValue = "id") String sort, @RequestParam(defaultValue = "asc") Sort.Direction direction) {
        List<Project> projects = service.readAll();
        List<ProjectDTO> response = mapper.map(projects);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDetailedDTO> readById(@PathVariable("id") UUID id) {
        Project project = service.readById(id);
        ProjectDetailedDTO response = mapper.map(project);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ProjectDetailedDTO> create(@RequestBody @Valid Project project) {
        Project created = service.create(project);
        ProjectDetailedDTO response = mapper.map(created);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") UUID id, @RequestBody @Valid Project project) {
        service.updateById(id, project);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
