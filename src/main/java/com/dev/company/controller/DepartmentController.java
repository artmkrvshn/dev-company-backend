package com.dev.company.controller;

import com.dev.company.aspect.TrackExecutionTime;
import com.dev.company.dto.DepartmentDTO;
import com.dev.company.entity.Department;
import com.dev.company.mapper.DepartmentMapper;
import com.dev.company.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@TrackExecutionTime
@RestController
@RequestMapping("/api/v1/departments")
public class DepartmentController {

    private final DepartmentService service;
    private final DepartmentMapper mapper;

    @Autowired
    public DepartmentController(DepartmentService service, DepartmentMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> readAll(@RequestParam(defaultValue = "id") String sort, @RequestParam(defaultValue = "asc") Sort.Direction direction) {
        List<Department> departments = service.readAll();
        List<DepartmentDTO> response = mapper.map(departments);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> readById(@PathVariable("id") UUID id) {
        Department department = service.readById(id);
        DepartmentDTO response = mapper.map(department);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<DepartmentDTO> create(@RequestBody @Valid Department department) {
        Department created = service.create(department);
        DepartmentDTO response = mapper.map(created);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") UUID id, @RequestBody @Valid Department department) {
        service.updateById(id, department);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
