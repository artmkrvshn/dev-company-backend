package com.dev.company.util;

import com.dev.company.entity.Department;
import com.dev.company.repository.DepartmentRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class DepartmentValidator implements Validator {

    private final DepartmentRepository departmentRepository;

    public DepartmentValidator(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Department.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Department department = (Department) target;
        String name = department.getName();

        if (departmentRepository.existsByName(name)) {
            errors.rejectValue("name", "", "A department with this name already exists.");
        }
    }
}
