package com.dev.company.service;

import com.dev.company.aspect.TrackExecutionTime;
import com.dev.company.entity.Employee;
import com.dev.company.exception.employee.EmployeeNotFoundException;
import com.dev.company.repository.EmployeeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@TrackExecutionTime
@Log4j2
@Service
@Transactional(readOnly = true)
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Employee create(Employee employee) {
        return repository.saveAndFlush(employee);
    }

    public Employee readById(UUID id) {
        Optional<Employee> employee = repository.readWithDepartmentAndPositionById(id);
        return employee.orElseThrow(() ->
                new EmployeeNotFoundException("An employee with this ID doesn't exist."));
    }

    public List<Employee> readAll() {
        return repository.findAll();
    }

    public List<Employee> readAll(Sort sort) {
        return repository.readAllWithDepartmentAndPosition(sort);
    }

    @Transactional
    public void updateById(UUID employeeId, Employee employee) {
        if (!repository.existsById(employeeId)) {
            throw new EmployeeNotFoundException("An employee with this ID doesn't exist.");
        }
        employee.setId(employeeId);
        repository.save(employee);
    }

    @Transactional
    public void deleteById(UUID id) {
        if (!repository.existsById(id)) {
            throw new EmployeeNotFoundException("An employee with this ID doesn't exist.");
        }
        repository.deleteById(id);
    }

}
