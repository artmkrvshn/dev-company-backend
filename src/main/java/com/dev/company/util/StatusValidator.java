package com.dev.company.util;

import com.dev.company.entity.Status;
import com.dev.company.repository.StatusRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class StatusValidator implements Validator {

    private final StatusRepository statusRepository;

    public StatusValidator(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Status.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Status status = (Status) target;
        String name = status.getName();

        if (statusRepository.existsByName(name)) {
            errors.rejectValue("name", "", "A status with this name already exists.");
        }
    }
}