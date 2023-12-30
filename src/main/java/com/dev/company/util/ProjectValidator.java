package com.dev.company.util;

import com.dev.company.entity.Project;
import com.dev.company.repository.ProjectRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProjectValidator implements Validator {

    private final ProjectRepository repository;

    public ProjectValidator(ProjectRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Project.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Project project = (Project) target;
        String name = project.getName();

        if (repository.existsByName(name)) {
            errors.rejectValue("name", "", "A project with this name already exists.");
        }
    }
}
