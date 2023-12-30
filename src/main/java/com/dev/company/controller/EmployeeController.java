package com.dev.company.controller;

import com.dev.company.aspect.TrackExecutionTime;
import com.dev.company.dto.EmployeeDTO;
import com.dev.company.entity.Employee;
import com.dev.company.mapper.EmployeeMapper;
import com.dev.company.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@TrackExecutionTime
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeService service;
    private final EmployeeMapper mapper;

    public EmployeeController(EmployeeService service, EmployeeMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> readAll(@RequestParam(defaultValue = "id") String sort, @RequestParam(defaultValue = "asc") Sort.Direction direction) {
        List<Employee> employees = service.readAll();
        List<EmployeeDTO> responses = mapper.map(employees);
        return ResponseEntity.ok(responses);
    }


    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> readById(@PathVariable(name = "id") UUID id) {
        Employee employee = service.readById(id);
        EmployeeDTO response = mapper.map(employee);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> create(@RequestBody @Valid Employee employee) {

        System.out.println(employee);

        Employee created = service.create(employee);
        EmployeeDTO response = mapper.map(created);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") UUID id, @RequestBody @Valid Employee employee) {
        service.updateById(id, employee);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
