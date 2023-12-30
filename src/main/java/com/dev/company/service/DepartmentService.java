package com.dev.company.service;

import com.dev.company.aspect.TrackExecutionTime;
import com.dev.company.entity.Department;
import com.dev.company.exception.ForeignKeyViolationException;
import com.dev.company.exception.department.DepartmentAlreadyExistsException;
import com.dev.company.exception.department.DepartmentNotFoundException;
import com.dev.company.repository.DepartmentRepository;
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
public class DepartmentService {

    private final DepartmentRepository repository;

    public DepartmentService(DepartmentRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Department create(Department department) {
//        if (repository.existsByName(department.getName())) {
//            throw new DepartmentAlreadyExistsException("A department with this name already exists.");
//        }
        return repository.saveAndFlush(department);
    }

    public Department readById(UUID id) {
        Optional<Department> department = repository.readById(id);
        return department.orElseThrow(() -> new DepartmentNotFoundException("A department with this ID doesn't exist."));
    }

    public List<Department> readAll() {
        return repository.findAll();
    }

    public List<Department> readAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Transactional
    public void updateById(UUID departmentId, Department department) {
        if (!repository.existsById(departmentId)) {
            throw new DepartmentNotFoundException("A department with this ID doesn't exist.");
        }
        if (repository.existsByName(department.getName())) {
            throw new DepartmentAlreadyExistsException("A department with this name already exists.");
        }
        department.setId(departmentId);
        repository.save(department);
    }

    @Transactional
    public void deleteById(UUID id) {
        if (!repository.existsById(id)) {
            throw new DepartmentNotFoundException("A department with this ID doesn't exist.");
        }
        if (repository.hasEmployees(id)) {
            throw new ForeignKeyViolationException("The department is still referenced in the \"employee\" table.");
        }
        repository.deleteById(id);
    }

}
