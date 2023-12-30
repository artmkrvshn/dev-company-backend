package com.dev.company.util;

import com.dev.company.entity.Position;
import com.dev.company.repository.PositionRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PositionValidator implements Validator {

    private final PositionRepository positionRepository;

    public PositionValidator(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Position.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
//        Position position = (Position) target;
//        String name = position.getName();
//        boolean hasProjectAccess = position.isHasProjectsAccess();
//
//        if (positionRepository.existsByNameAndHasProjectsAccess(name, hasProjectAccess)) {
//            errors.rejectValue("name", "", "A position with this name already exists.");
//        }
    }
}