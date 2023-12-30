package com.dev.company.service;

import com.dev.company.aspect.TrackExecutionTime;
import com.dev.company.entity.Project;
import com.dev.company.exception.project.ProjectAlreadyExistsException;
import com.dev.company.exception.project.ProjectNotFoundException;
import com.dev.company.repository.EmployeeRepository;
import com.dev.company.repository.ProjectRepository;
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
public class ProjectService {

    private final ProjectRepository repository;
    private final EmployeeRepository employeeRepository;

    public ProjectService(ProjectRepository repository, EmployeeRepository employeeRepository) {
        this.repository = repository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public Project create(Project project) {
        if (repository.existsByName(project.getName())) {
            throw new ProjectAlreadyExistsException("A project with this name already exists.");
        }
        return repository.saveAndFlush(project);
    }

    public Project readById(UUID id) {
        Optional<Project> project = repository.readWithChiefAndEmployeesAndStatuses(id);
        return project.orElseThrow(() ->
                new ProjectNotFoundException("A project with this ID doesn't exist."));
    }

    public List<Project> readAll() {
        return repository.readAllWithStatusAndChief();
    }

    public List<Project> readAll(Sort sort) {
        return repository.readAllWithStatusAndChief(sort);
    }

    @Transactional
    public void updateById(UUID projectId, Project project) {
        if (!repository.existsById(projectId)) {
            throw new ProjectNotFoundException("A project with this ID doesn't exist.");
        }
//        if (repository.existsByName(project.getName())) {
//            throw new ProjectAlreadyExistsException("A project with this name already exists.");
//        }
//        employeeRepository.saveAll(project.getEmployees());
        project.setId(projectId);
        repository.save(project);
    }

    @Transactional
    public void deleteById(UUID id) {
        if (!repository.existsById(id)) {
            throw new ProjectNotFoundException("A project with this ID doesn't exist.");
        }
        repository.deleteById(id);
    }

}
